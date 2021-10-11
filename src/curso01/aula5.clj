(ns curso01.aula5)

; maps (key, value)
(println {"a" 1, "b" 2})

(def estoque {"Mochila" 10, "Camiseta" 20})
(println estoque)

; usando simbolos
(def estoque {:mochila 10, :camiseta 20})
(println estoque)

; assoc: add new pair to a map
(println (assoc estoque :cadeira 5))

; update a value for a given key
(println (update estoque :mochila inc))

; dissoc: removes a key/value from a map
(println (dissoc estoque :mochila))

(println "--------------------")
;;mapas aninhados
(def pedido {:mochila {:quantidade 10, :preco 100}
             :camiseta {:quantidade 4, :preco 50}})

(println pedido)

; get
(println (get (get pedido :mochila) :quantidade))
(println ((pedido :mochila) :quantidade))
(println (:quantidade (:mochila pedido)))
; threading
(println (-> pedido :mochila :quantidade))                  ;; a mas usada

;update in a map
(println (update-in pedido [:mochila :quantidade] inc))