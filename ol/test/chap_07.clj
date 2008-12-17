(ns ol.test.chap-07
  (:use fact ol.chap-07 ol.test.util))

(def zeros [0])
(def rand-neg-ints (filter neg? (rand-ints)))
(def rand-pos-ints (filter pos? (rand-ints)))
(defn die [] (throw (Exception. "This argument should not be evaluated")))

(fact "nil! nils out an atom"
  [at [(atom :foo) (atom nil)]]
  (= nil (nil! at)))

(fact "nif evals and returns its third arg for numeric zero"
  [z zeros]
  (= :z (nif z (die) :z (die))))

(fact "nif evals and returns its second arg for numeric positive"
  [z rand-pos-ints]
  (= :p (nif z :p (die) (die))))
      
(fact "nif evals and returns its fourth arg for numeric negative"
  [z rand-neg-ints]
  (= :n (nif z (die) (die) :n)))
      
(fact "our-when executes body forms if its test is true"
  []
  (= "really!" (our-when (= 1 1) "one is one" "really!")))

(fact "our-when does not execute body forms if test is false"
  []
  (= nil (our-when (= 1 2) (die))))

(fact "when-bind binds a value and executes a body if test is true"
  []
  (= "a is 2" (when-bind [a (+ 1 1)] (str "a is " a))))

(fact "when-bind does nothing and returns nil if test is false"
  []
  (= nil (when-bind [a (= 1 2)] (die))))

(fact "when-bind blows up when passed a form legal for binding but not evaluation"
  []
  (let [ex (throws? Exception (eval '(when-bind [[a & b] [1 2]] [a b])))]
    (re-find #"Unable to resolve symbol: &" (.getMessage ex))))
      
(fact "while executes repeatedly while test is true"
  [r [(ref 0) (ref 1) (ref 10)]]
  (= 0 (do
	 (our-while (> @r 0) 
		    (dosync (alter r dec)))
	 @r)))
	 
(print-results (verify-facts 'ol.test.chap-07))

    
  

