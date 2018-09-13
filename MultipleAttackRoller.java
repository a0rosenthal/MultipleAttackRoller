//Usage:
// java MultipleAttackRoller [number of attacks] [bonus to hit] [target AC] [Dice formula]

// for example: java MultipleAttackRoller 10 3 15 2d6+1
// This would roll 10 attacks at a +1 modifier against an Armor Class of 15.
// The attacks that hit would do 2d6+1 damage, and any that crit would do 4d6+1.

import java.util.Random;

public class MultipleAttackRoller {

  public static void main(String[] args){

    if (args.length != 3 && args.length != 4) {
      System.out.println(
        "Usage:\njava MultipleAttackRoller [number of attacks] [bonus to hit] [target"
            + " AC] [Damage Dice]");
      return;
    }

    int numAttacks = Integer.parseInt(args[0]);
    int attackBonus = Integer.parseInt(args[1]);
    int targetAC = Integer.parseInt(args[2]);

    int hits = 0;
    int crits = 0;
    Random roller = new Random();
    int roll;

    for(int i = 0; i<numAttacks; i++){
      roll = roller.nextInt(20)+1;
      if(roll == 20){
        crits++;
      }else if(roll + attackBonus >= targetAC){
        hits++;
      }
    }

    System.out.println(hits + " hits, " + crits + " crits.");

//Rolling Damage/////////////////////////////////////////////////////

    if(args.length == 4){
      String damageFormula = args[3];
      int numDice, dieType;
      int mod = 0;
      StringBuilder numHolder = new StringBuilder();
      int i = 0;
      boolean d = false;
      char letter;
      while(!d && i<damageFormula.length()){
        letter = damageFormula.charAt(i);
        if(letter == 'd'){ d = true;}
        else{
          numHolder.append(letter);
        }
        i++;
      }

      if(!d){
        int damPerHit = Integer.parseInt(numHolder.toString());
        int damage = (damPerHit * hits) + (damPerHit * crits);
        System.out.println( damage + " damage total." );
        return;
      }

      if(numHolder.toString().length() == 0){
        numDice = 1;
      } else {
        numDice = Integer.parseInt(numHolder.toString());
      }

      boolean plus = false;
      numHolder = new StringBuilder();
      while(!plus && i<damageFormula.length()){
        letter = damageFormula.charAt(i);
        if(letter == '+' || letter == '-'){
          plus = true;
        } else {
          numHolder.append(letter);
        }
        i++;
      }

      dieType = Integer.parseInt(numHolder.toString());

      if(plus){
        if(damageFormula.charAt(i-1) == '-'){
          mod = -1;
        } else{ mod = 1; }
        numHolder = new StringBuilder();
        while(i<damageFormula.length()){
          numHolder.append(damageFormula.charAt(i));
          i++;
        }
        mod = mod * Integer.parseInt(numHolder.toString());
      }

      int damage = 0;

      for(i = 0; i<hits; i++){
        damage += roll(numDice, dieType, mod);
      }
      for(i = 0; i<crits; i++){
        damage += roll(2*numDice, dieType, mod);
      }
      System.out.println(damage + " total damage");
    }
    return;
  }

  public static int roll (int numDice, int dieType, int mod){
    Random roller = new Random();
    int total = 0;
    for(int i = 0; i<numDice; i++){
      total += roller.nextInt(dieType) + 1;
    }
    total += mod;
    if(total < 0) return 0;
    return total;
  }

}
