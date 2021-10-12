(ns loja.aula1)
; aula 1 curso coleçoes

; Map implementation
(defn meu-map
  [function sequence]
  (let [firstItem (first sequence)
        tail (rest sequence)]
    (if (not (nil? firstItem))
      (do
        (function firstItem)
        (meu-map function tail)))))


(meu-map println ["a" "b" "c"])
(meu-map println ["a" false "c"])
(meu-map println ["a" nil "c"])                             ;; does not print all the sequence
(meu-map println [])
(meu-map println nil)
; (meu-map println (range 100000))  ; stackoverflow problem

(defn meu-map
  [function sequence]
  (let [firstItem (first sequence)
        tail (rest sequence)]
    (if (not (nil? firstItem))
      (do
        (function firstItem)
        (recur function tail)))))                           ;uses recur keyword for tail recursion (call the same function)

(meu-map println (range 100000))  ; stackoverflow problem does not occur
