(ns hospital.utils)
(defn task [f]
  (.start (Thread. (fn [] (f)))))

