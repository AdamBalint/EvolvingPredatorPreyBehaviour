

charges = ["0", "0.33", "0.67", "1"]
radius = [("2","40"),("5","30")]

hidden_nodes = ["15", "30", "60"]
game_fall = ["true", "false"]

count = -3
for pred_charge in charges:
    for pred_hidden in hidden_nodes:
        for rad in radius:
            count += 3
            f = open(str(count)+".sh","w+")
            f.write("java -jar pvpv1.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[1] + "\n")
            f.write("java -jar pvpv1.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[0] + "\n")
            f.flush()
            f.close()
            f = open(str(count+1)+".sh","w+")
            f.write("java -jar pvpv2.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[1] + "\n")
            f.write("java -jar pvpv2.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[0] + "\n")
            f.flush()
            f.close()
            f = open(str(count+2)+".sh","w+")
            f.write("java -jar pvpv3.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[1] + "\n")
            f.write("java -jar pvpv3.jar " + pred_charge + " " + pred_hidden + " " + pred_charge + " " + pred_hidden + " " + rad[0] + " " + rad[1] + " " + game_fall[0] + "\n")
            f.flush()
            f.close()