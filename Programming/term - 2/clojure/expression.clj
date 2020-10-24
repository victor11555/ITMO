(defn oper [p] (fn [& args] (fn [vars] (apply p (mapv #(% vars) args))))) 
 
(def add (oper +)) 
(def subtract (oper -)) 
(def multiply (oper *)) 
(def divide (oper #(/ (double %1) %2))) 
(def negate subtract) 
(def exp (oper #(Math/exp %))) 
(def ln (oper #(Math/log (Math/abs %)))) 
 
(defn constant [val] (fn [& args] val)) 
(defn variable [val] #(get % val)) 
 
(def operMap { '+ add, 
    '- subtract, 
    '* multiply, 
    '/ divide, 
    'exp exp, 
    'ln ln, 
    'negate negate}) 
 
(defn parse [expr] 
    (cond 
    (number? expr) (constant expr) 
    (symbol? expr) (variable (str expr)) 
    :else (apply (get operMap (first expr)) (mapv parse (rest expr))))) 
 
(defn parseFunction [expr] (parse (read-string expr)))