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

(testa-atomao)