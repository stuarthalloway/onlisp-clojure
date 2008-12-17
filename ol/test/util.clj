(ns ol.test.util)

(defmacro throws? [cl & body]
  `(try ~@body
     (throw (RuntimeException. (str "Expected " ~cl)))
     (catch ~cl e# e#)))

