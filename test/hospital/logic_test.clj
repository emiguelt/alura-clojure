(ns hospital.logic-test
  (:require [hospital.logic :refer :all]
            [clojure.test :refer :all]
            [hospital.model :as h.model]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check.properties :as prop]))

(deftest cabe-na-fila?-test
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera []} :espera)))
  ; group tests in one case
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera [1 2 3]} :espera))
    (is (cabe-na-fila? {:espera [1]} :espera)))
  ; test many combination with generator
  (testing "Cabe na fila con varios vetores"
    ;generate length 0 to 4 vectors, 10 times
    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4) 10)]
      (is (cabe-na-fila? {:espera fila} :espera))))
  (testing "Que nao cabe quando a fila esta cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))
  (testing "Que nao cabe quando a fila esta mas cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5 6]} :espera))))
  (testing "Que nao cabe quando a fila nao existe"
    (is (not (cabe-na-fila? {:espera []} :outra))))
  )

(deftest chega-em-with-response-test
  (testing "aceita pessoas enquanto cabem na fila"
    (is (= {:hospital {:espera [1]} :result :success} (chega-em-with-response {:espera []} :espera 1)))
    (is (= {:hospital {:espera [1 4 6 2]} :result :success} (chega-em-with-response {:espera [1 4 6]} :espera 2)))
    )
  (testing "nao aceita pessoas quando a fila esta cheia"
    (is (= {:hospital {:espera [1 0 2 3 4]} :result :error-adding-person}
           (chega-em-with-response {:espera [1 0 2 3 4]} :espera 5)))))


; O metodo _tranfere_ depende da estrutura de datos, entao falha com vetor, mas funciona com fila
(deftest transfere-test
  (testing "Transfere corretamente uma pessoa"
    (is (= {:espera [] :raio-x [1]}
           (transfere {:espera (h.model/new-queue 1) :raio-x h.model/empty-queue} :espera :raio-x)))
    (is (= {:espera [3] :raio-x [2 1]}
           (transfere {:espera (h.model/new-queue 1 3) :raio-x (h.model/new-queue 2)} :espera :raio-x))))
  (testing "Check assertion pre y post"
    (is (thrown? AssertionError (transfere nil :espera :raio-x)))
    ))


(defspec coloca-uma-pessoa-em-filas-menores-de-5 100
         (prop/for-all
           [fila (gen/vector gen/string-alphanumeric 0 4)
            pessoa gen/string-alphanumeric]
           (is (= {:espera (conj fila pessoa)}
                  (chega-em {:espera fila} :espera pessoa)))))

(def nome-aleatorio-gen
  (gen/fmap clojure.string/join
            (gen/vector gen/char-alpha-numeric 5 10)))

(defn transforma-vetor-em-fila [vector]
  (reduce conj h.model/empty-queue vector))

(def fila-nao-cheia-gen
  (gen/fmap
    transforma-vetor-em-fila
    (gen/vector nome-aleatorio-gen 0 4)))

(defn total-de-pacientes [hospital]
  (reduce + (map count (vals hospital))))

(defspec transfere-tem-que-manter-a-quantidade-de-pessoas 100
         (prop/for-all
           [espera fila-nao-cheia-gen
            raio-x fila-nao-cheia-gen
            ultrasom fila-nao-cheia-gen
            vai-para (gen/elements [:raio-x :ultrassom])]
           (let [hospital-inicial {:espera espera :raio-x raio-x :ultrassom ultrasom}
                 hospital-final (transfere hospital-inicial :espera vai-para)
                 ]
             (is (= (total-de-pacientes hospital-inicial)
                    (total-de-pacientes hospital-final)))
             true
             )))