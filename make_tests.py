

charges = ["0", "0.25", "0.5", "0.75","1"]
radius = [("0.01","0.1"),("0.1","2"),("2","40")]

hidden_nodes = ["15", "30", "60"]
game_fall = ["true", "false"]


for fall in game_fall:
    for rad in radius:
        f = open(fall+"-"+rad[0]+".sh","w+")
        for pred_charge in charges:
            for pred_hidden in hidden_nodes:
                f.write("java -jar pvp.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + fall + "\n")
        f.flush()
        f.close()