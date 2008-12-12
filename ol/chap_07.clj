(ns ol.chap-07)

; boring, since dosycn and ref-set are not special forms
(defn nil! [ref]
  (dosync (ref-set ref nil)))

; to have a simple macro that does something
(defmacro defnil [name]
  `(def ~name nil))

; OL shows two versions, one to demonstrate PITA not using `
; no obvious Number/signum in Clojure
(use '[clojure.contrib.fcase :only (case)])
(defmacro nif [expr pos zer neg]
  `(case (Integer/signum ~expr) 
	 -1 ~neg
	 0 ~zer
	 1 ~pos))

; almost a direct translation (progn -> do
(defmacro our-when [test & body]
  `(if ~test
     (do
       ~@body)))

; test with a reference
; (while (> @x 0) (println @x) (dosync (alter x dec)))
(defmacro our-while [test & body]
  `(loop []
     (when ~test
       ~@body
       (recur))))

; not Clojurish, use when-let
(defmacro when-bind [bindings & body]
  (let [[form tst] bindings]
    `(let [~form ~tst]
       (when ~form
	 ~@body))))
