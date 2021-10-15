(ns hospital.utils)
(defn task [f]
  (.start (Thread. (fn [] (f)))))

(defn safe-try [f]
  (try (f)
       (catch Exception e (.getMessage e))))