(ns hospital.core
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]))

(let [hospital-america (h.model/novo-hospital)]
  (pprint hospital-america))