(ns hospital.model)

(def empty-queue clojure.lang.PersistentQueue/EMPTY)

(defn new-queue [& elems] (apply conj empty-queue elems))

(defn novo-hospital []
  {:espera empty-queue
   :laboratorio1 empty-queue
   :laboratorio2 empty-queue
   :laboratorio3 empty-queue})
