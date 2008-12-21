(ns ol.test.util)

(defmacro throws? [cl & body]
  `(try ~@body
     (throw (RuntimeException. (str "Expected " ~cl)))
     (catch ~cl e# e#)))

(defmacro expect
  "Evaluates expr and returns it, or throws an exception 
if it does not evaluate to logical true."
  [& x]
  `(let [result# ~x]
     (if result#
       result#
       (throw (new Exception (str "Expect failed: " (pr-str '~x)))))))

(defmacro ns-reload! [ns]
  `(do
     (if (find-ns '~ns)
       (remove-ns '~ns))
     (require :reload-all '~ns)))
