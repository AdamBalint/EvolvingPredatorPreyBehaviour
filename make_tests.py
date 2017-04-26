

charges = ["0", "0.33", "0.67", "1"]
radius = [("0.01","0.1"),("0.1","2")]

hidden_nodes = ["15", "30", "60"]
game_fall = ["true", "false"]

count = 0
for pred_charge in charges:
    for pred_hidden in hidden_nodes:
        for rad in radius:
            count += 1
            f = open(str(count)+".sh","w+")
            f.write("java -jar pvpv2.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[1] + "\n")
            f.write("java -jar pvpv2.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[0] + "\n")
            f.write("java -jar pvpv3.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[1] + "\n")
            f.write("java -jar pvpv3.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[0] + "\n")
            f.flush()
            f.close()