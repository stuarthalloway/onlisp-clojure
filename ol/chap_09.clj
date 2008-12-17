(ns ol.chap-09)

; wrong, but fails fast. Clojure puts _limit_ into a namespace
; Clojure basically forces you to gensym to get the code to run.
(defmacro bad-for [[var start stop] & body]
  `(loop [~var ~start limit ~stop]
     (if (< ~var ~stop)
       (do
	 ~@body
	 (recur (inc ~var) limit)))))

; going out of my way to introduce evil capture
(defmacro evil-for [[var start stop] & body]
  `(loop [~var ~start ~'limit ~stop]
     (if (< ~var ~'limit)
       (do
	 ~@body
	 (recur (inc ~var) ~'limit)))))

; when in doubt, append #
(defmacro good-for [[var start stop] & body]
  `(loop [~var ~start limit# ~stop]
     (if (< ~var limit#)
       (do
	 ~@body
	 (recur (inc ~var) limit#)))))

; works. ol.chap-09/w and w never collide
(def w (ref []))
(defmacro gripe [warning]
  `(do
     (dosync (alter w conj ~warning))
     nil))

(defn sample-ratio [v w]
  (let [vn (count v) wn (count w)]
    (if (or (< vn 2) (< wn 2))
      (gripe "sample < 2")
      (/ vn wn))))
       
; wrong
(defmacro bad-gripe [warning]
  `(do
     (dosync (alter ~'w conj ~warning))
     nil))

; wrong
; surprise win: immutable local bindings save us
(defn bad-sample-ratio [v w]
  (let [vn (count v) wn (count w)]
    (if (or (< vn 2) (< wn 2))
      (bad-gripe "sample < 2")
      (/ vn wn))))

(defn next-value [x] (inc x))
(defmacro mac [x] `(next-value ~x))



