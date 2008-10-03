(ns chap_02
    (use util))

(defn even [x] (zero? (rem x 2)))

(defn our-filter [fn seq]
  (when (seq seq)
    (if (fn (first seq))
      (lazy-cons (first seq) (our-filter fn (rest seq)))
      (recur fn (rest seq)))))

(let [y 7]
  (defn scope-test [x]
    (list x y)))

(defn list+ [lst n]
  (map #(+ n %) lst))

(let [counter (ref 0)]
  (defn new-id [] (dosync (alter counter inc)))
  (defn reset-id [] (dosync (ref-set counter 0))))

(defn make-adder [n]
  (let [n (ref n)]
    (fn add
      ([x] 
	 (+ @n x))
      ([x new-n] 
	 (dosync (ref-set n new-n))
	 (add x)))))
	 

(print-examples
 (sort '(1 4 2 5 6 7 3))
 (filter (complement even?) (range 1 8))
 (filter even? (range 1 8))
 (list+ [1 2 3] 2)
 (new-id)
 (new-id)
 (reset-id))

; next: p. 22 count-instances
