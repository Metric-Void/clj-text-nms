(ns clj-text-nms.help (:gen-class))

; Help documentations for the game.

(def planet-envs {
    :lush   "Lush - This planet is full of creatures. The atmosphere is dense and gravity is normal."
    :rad    "Radioactive - This planet is radioactive. Creatures on this planet have adapted to this envinronment and probably dangerous.\n
            Radioactive minerals can be found abundant here. \n
            Life support will drop as time goes."
    :cold   "Cold - This is a frosty planet with ice everywhere.\n
            Solid Carbon Dioxide can be easily found here.\n
            Life support will drop as time goes.\n"
    :hot    "Hot - This planet is burning. Everything looks red.\n
            Sulfur can be found abundant here.\n
            Life support will drop as time goes."
    :tox    "The atmosphere on this planet is toxic. \n
            Ammonium can be found abundant here. \n
            Life support will drop as time goes."
    :exotic "This planet have no atmosphere and an apparent lack of life. Life support drops rapidly.\n
             However, metal resources can be found abundant here."
})