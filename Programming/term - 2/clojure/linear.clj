(defn oper [f] (fn [& arg] (apply mapv f arg)))

(def v+ (oper +))
(def v- (oper -))
(def v* (oper *))
(def vd (oper /))

(def m+ (oper v+))
(def m- (oper v-))
(def m* (oper v*))
(def md (oper vd))

(def c+ (oper m+))
(def c- (oper m-))
(def c* (oper m*))
(def cd (oper md))

(defn scalar [a, b] (apply + (mapv * a b)))
(defn vect [v1 v2] (vector
                        (- (* (v1 1) (v2 2)) (* (v1 2) (v2 1)))
                        (- (* (v1 2) (v2 0)) (* (v1 0) (v2 2)))
                        (- (* (v1 0) (v2 1)) (* (v1 1) (v2 0)))))

(defn *s [s] (fn [v] (mapv (partial * s) v)))
(defn v*s [v s] ((*s s) v))

(defn m*s [m s] (mapv (*s s) m))
(defn transpose [m] (apply mapv vector m))
(defn m*v [m v] (mapv (fn [v2] (scalar v v2)) m))
(defn m*m [m1 m2] (mapv (fn [v1] (m*v (transpose m2) v1)) m1))