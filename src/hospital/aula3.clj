(ns hospital.aula3
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn testa-atomao []
  (let [hospital-america (atom {:espera h.model/fila_vazia})]
    (println hospital-america)
    (pprint hospital-america)
    (pprint (deref hospital-america))
    (pprint @hospital-america)
    ; Não é assim que eu altero conteúdo dentro de um átomo
    (pprint (assoc @hospital-america :laboratorio1 h.model/fila_vazia))
    (pprint @hospital-america)
    ; Essa é uma das maneiras de alterar conteúdo dentro de um átomo
    (swap! hospital-america assoc :laboratorio1 h.model/fila_vazia)
    (pprint @hospital-america)
    (swap! hospital-america assoc :laboratorio2 h.model/fila_vazia)
    (pprint @hospital-america)
    ; Update tradicional imutável com deferência que não trará efeito
    (update @hospital-america :laboratorio1 conj "111")
    (swap! hospital-america update :laboratorio1 conj "111")
    (pprint hospital-america)))

; (testa-atomao)

(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "após inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

(simula-um-dia-em-paralelo)