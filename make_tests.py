

charges = ["0", "0.33", "0.67", "1"]
radius = [("0.01","0.1"),("0.1","2")]

hidden_nodes = ["15", "30", "60"]
game_fall = ["true", "false"]

count = 0
for pred_charge in charges:
    for pred_hidden in hidden_nodes:
        count += 1
        for fall in game_fall:
            
            f = open(fall+"-"+str(count)+".sh","w+")
            for rad in radius:
                f.write("java -jar pvp.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + fall + "\n")
            f.flush()
            f.close()