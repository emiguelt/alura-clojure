(ns loja.aula2)

; function to count elements of a collection
(defn conta
  ([elements] (conta 0 elements))
  ([accum elements]
   (if (seq elements)
     (recur (inc accum) (next elements))
     accum)))

(println (conta (range 10)))
(println (conta []))
(println (conta nil))

; using loop
(defn conta2
  [elements]
  (loop [accum 0
         left-elements elements]
    (if (seq left-elements)
      (recur (inc accum) (next left-elements))
      accum)))

(println "Using loop")
(println (conta2 (range 10)))
(println (conta2 []))
(println (conta2 nil))
