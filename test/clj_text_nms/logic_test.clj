(ns clj-text-nms.logic-test
  (:require [clojure.test :refer :all]
            [clj-text-nms.core :refer :all]
            [clj-text-nms.logic :refer :all])
  (:use midje.sweet))

(facts "About Hashmap addition"
    (fact "No conflict"
        (combine-hashmap {:a 1} {:b 2}) => {:a 1, :b 2}
        (combine-hashmap {:a 1} {:b 2, :c 3}) => {:a 1, :b 2, :c 3}
    )
    (fact "A single conflict"
        (combine-hashmap {:a 1, :b 2} {:b 2}) => {:a 1, :b 4}
        (combine-hashmap {:g 4 :p 3} {:p 5, :d 4}) => {:g 4, :p 8, :d 4}
    )
    (fact "Orphan keys"
        (combine-hashmap {:a 4, :b 3} {:b 6, :c 7}) => {:a 4, :b 9, :c 7}
    )
)

(facts "About Hashmap subtraction"
    (fact "No common item"
        (subtract-hashmap {:a 1} {:b 2}) => nil
        (subtract-hashmap {:a 1} {:b 2, :c 3}) => nil
    )
    (fact "An item can be removed."
        (subtract-hashmap {:a 1, :b 2} {:b 1}) => {:a 1, :b 1}
        (subtract-hashmap {:g 4, :p 3} {:p 2}) => {:g 4, :p 1}
    )
    (fact "Make sure that items with count 0 are removed"
        (subtract-hashmap {:a 1, :b 1} {:a 1}) => {:b, 1}
        (subtract-hashmap {:a 1, :b 1} {:a 1, :b 1}) => {}
    )
    (fact "Not enough item"
        (subtract-hashmap {:a 4} {:a 5}) => nil
        (subtract-hashmap {:b 3, :c 10} {:b 5, :c 5}) => nil
    )
    (fact "Orphan keys"
        (subtract-hashmap {:a 4, :b 3} {:b 6, :c 7}) => nil
    )
    (fact "Weirdos"
        (subtract-hashmap {} {}) => {}
        (subtract-hashmap {} {:a 1}) => nil
        (subtract-hashmap {:a 1} {}) => {:a 1}
    )
)