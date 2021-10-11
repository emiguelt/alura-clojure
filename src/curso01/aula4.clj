(ns curso01.aula4)

(def precos [30 700 1000])

(println precos)
(println (precos 0))
;; other way to get data from vector
(println (get precos 1))

;; this way there is no exception, returns null
(println (get precos 100))

;; this way a default value is passed
(println (get precos 100 0))

; conj: create a new collection with an added value
(println (conj precos 50))

; update: create a new collection, modifying a value in a given index with a function
(println (update precos 0 inc))

; map: convert a collection with a given function
(println (map inc precos))

; filter: create a new collection with the data where the predicate is true
(println (filter even? (range 10)))

; reduce: applies a function between the elements of the collection to get a single result
(println (reduce + (range 10)))
; with initial value
(println (reduce + 100 (range 10)))
; if collection is empty, the initial value is taken, if not value is passed a exception is thrown
(println (reduce + 100 []))
