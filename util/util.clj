(ns util)

(defmacro print-examples [& forms]
  `(do 
     ~@(map (fn [form]
	      `(do 
		 (println '~form)
		 (println "->" ~form)))
	    forms)))