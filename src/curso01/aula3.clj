(ns curso01.aula3)

; PREDICATED
(defn aplica-desconto? [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  "retorna o valor com desconto se for estritamente maior do que 100"
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 1 10)
          desconto (* valor-bruto taxa-de-desconto)]
      (println "Calculando desconto....")
      (- valor-bruto desconto))
    valor-bruto))

(println (valor-descontado 1000))
(println (valor-descontado 100))
