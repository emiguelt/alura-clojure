(ns hospital.logic-test
  (:require [clojure.test :refer :all])
  (:require [hospital.logic :refer :all]
            [hospital.model :as h.model]))

(deftest cabe-na-fila?-test
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera []} :espera)))
  ; group tests in one case
  (testing "Que cabe na fila"
    (is (cabe-na-fila? {:espera [1 2 3]} :espera))
    (is (cabe-na-fila? {:espera [1]} :espera)))
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