(ns hospital.aula4
  (:use [clojure.pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "após inserir" pessoa))

(defn simula-um-dia-em-paralelo-com-mapv
  "Simulação utilizando mapv para forçar quase que imperativamente a execução"
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]]
    (mapv #(.start (Thread. (fn [] (chega-sem-malvado! hospital %)))) pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

;(simula-um-dia-em-paralelo-com-mapv)

(defn simula-um-dia-em-paralelo-com-mapv-refatorada
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        startThread #(.start (Thread. (fn [] (chega-sem-malvado! hospital %))))]
    (mapv startThread pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

; (simula-um-dia-em-paralelo-com-mapv-refatorada)

(defn start-thread-de-chegada
  ([hospital]
   (fn [pessoa] (start-thread-de-chegada hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        start (start-thread-de-chegada hospital)]
    (mapv start pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

; (simula-um-dia-em-paralelo-com-mapv-extraida)

(defn start-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-sem-malvado! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas ["111", "222", "333", "444", "555", "666"]
        start (partial start-thread-de-chegada hospital)]
    (mapv start pessoas)
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo-com-partial)