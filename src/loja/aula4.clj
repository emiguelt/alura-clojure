(ns loja.aula4
  (:require [loja.logic :as l.logic]
           [loja.db :as l.db]))

(let [resumo (l.logic/resumo-usuario (l.db/todos-pedidos))]
  (println "Resumo" resumo)
  (println "Ordenado por preco" (sort-by :totales resumo))
  (println "Ordenado ao contrario" (reverse (sort-by :totales resumo)))
  (println "Primeiro " (first resumo))
  (println "Second" (second resumo))
  (println "nth 1" (nth resumo 1))
  (println "get 1" (get resumo 1))
  (println "take 2" (take 2 resumo))
  (println "Filter > 200" (filter #(> (:totales %) 200) resumo))
  (println "Some (exist?) > 200" (some #(> (:totales %) 200) resumo)))