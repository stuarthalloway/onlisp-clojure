(ns ol.chap-10)

; wrong: mutliple evaluation
(defmacro m-e-for [[idx start stop] & body]
  `(loop [~idx ~start]
     (if (< ~idx ~stop)
       (do
	 ~@body
	 (recur (inc ~idx))))))

; wrong bad order of evaluation
(defmacro b-o-for [[idx start stop] & body]
  `(loop [limit# ~stop ~idx ~start]
     (if (< ~idx limit#)
       (do
	 ~@body
	 (recur limit# (inc ~idx))))))

(defn nth-a [n coll]
  (if (= n 0)
    (first coll)
    (nth-a (dec n) (rest coll))))
 
; wrong: infinite loop
(defmacro nth-b [n coll]
  `(if (= ~n 0)
     (first ~coll)
     (nth-b (dec ~n) (rest ~coll))))

; better: expanded into loop/recur
(defmacro nth-c [n coll]
  `(loop [n# ~n coll# ~coll]
     (if (= n# 0)
       (first coll#)
       (recur (dec n#) (rest coll#)))))

; expanded into internal function
(defmacro nth-e [n coll]
  `((fn [n# coll#]
      (if (= n# 0)
	(first coll#)
	(recur (dec n#) (rest coll#))))
    ~n ~coll))