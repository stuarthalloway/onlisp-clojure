(ns ol.test.chap-09
  (:use fact ol.chap-09 ol.test.util))

(fact "bad-for fails to let qualified name"
  []
  (let [ex (throws? Exception (eval '(bad-for [i 1 5] (println i))))]
    (re-find #"Can't let qualified name" (.getMessage ex))))

(fact "evil-for works for simple loops"
  []
  (= "1234" (with-out-str (evil-for [i 1 5] (print i)))))

(fact "evil-for fails if calling context binds limit"
  []
  (= "" (with-out-str (let [limit 5] 
			    (evil-for [i 1 10] 
			      (if (> i limit) (print i)))))))

(fact "good-for works for simple loops"
  []
  (= "1234" (with-out-str (good-for [i 1 5] (print i)))))

(fact "good-for still works if calling context binds limit"
  []
  (= "6789" (with-out-str (let [limit 5] 
			    (good-for [i 1 10] 
			      (if (> i limit) (print i)))))))

(fact "sample-ratio can gripe safely"
  []
  (and (= nil
	  (do
	    (dosync (ref-set w []))
	    (sample-ratio [] [])))
       (= ["sample < 2"] @w)))
		 
(fact "bad-sample-ratio blocked from using captured variable by mutable/immutable distinction"
 []
 (let [ex (throws? ClassCastException (bad-sample-ratio [] []))]
    (re-find #"java.lang.ClassCastException" (.getMessage ex))))   

(fact "simple macro function expansions are not affected by let"
  []
  (= 11 (let [next-value #(dec %)] (mac 10))))

(fact "simple macro function expansions *are* affected by binding"
  []
  (= 9 (binding [next-value #(dec %)] (mac 10))))

(print-results (verify-facts 'ol.test.chap-09))
