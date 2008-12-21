(ns ol.test.chap-10
  (:use fact ol.chap-10 ol.test.util 
	[clojure.contrib.seq-utils :only (flatten)]))

(fact "m-e-for works for simple arguments"
 []
 (expect = "0123456789" 
	 (with-out-str (m-e-for [i 0 10] (print i)))))
  
(fact "m-e-for evaluates stop each time through the loop"
  []
  (expect = 
	  "1234"
	  (with-out-str (let [a (atom 10)]
			  (m-e-for [i 1 (swap! a dec)] (print i))))))

(fact "b-o-for evaluates stop before start"
  []
  (expect = 
	  ""
	  (with-out-str (let [a (atom 1)]
			  (b-o-for [i @a (swap! a inc)] (print i))))))

(fact "b-o-for works for simple arguments"
 []
 (expect = "0123456789" 
	 (with-out-str (b-o-for [i 0 10] (print i)))))

(fact "the expansion of nth-b contains nth-b"
  []
  (some #(= 'ol.chap-10/nth-b %)
	(flatten (macroexpand-1 '(nth-b a b)))))

(def c-at-2 [[1 2 :c 3 4][:a :b :c :d :e]])

(fact "nth-a works"
  [coll c-at-2]
  (= :c (nth-a 2 coll)))
 
(fact "nth-c works"
  [coll c-at-2]
  (= :c (nth-c 2 coll)))

(fact "nth-e works"
  [coll c-at-2]
  (= :c (nth-e 2 coll)))

(print-results (verify-facts 'ol.test.chap-10))