/*
    Allegiance Handler is a non-data class designed to manage various tasks related to allegiance
 */
public class AllegianceHandler {
    public AllegianceHandler(){
        //do nothing
    }

    /**
     * returns true if allegiance "me" should attack the Human of allegiance "other"
     * @param me - allegiance of person checking
     * @param other - allegiance of other person
     * @return boolean true if they should attack
     */
    public boolean allegianceInteraction(String me, String other){
        return switch (me) {
            default -> false;//assume non-hostility
            case "Alliance" ->//the alliance
                    allianceInteraction(other);
            case "Imperial" -> imperialInteraction(other);
            case "Rebel" -> rebelInteraction(other);
            case "Insurgent" -> insurgentInteraction(other);
            case "Vlakian" -> vlakianInteraction(other);
            case "Terran" -> terranInteraction(other);
        };
    }

    /**
     * helper function that returns true if Alliance would attack person "other"
     * @param other - other person's allegiance
     * @return true if hostile
     */
    private boolean allianceInteraction(String other){
        return switch (other) {
            case "Alliance" ->//the Alliance
                    false;
            case "Imperial" ->//imperial Armadran
                    true;
            case "Rebel" ->//Armadran rebellion
                    false;
            case "Insurgent" ->//North Armadran insurgency
                    false;
            case "Vlakian" ->//Vlakian
                    true;
            case "Terran" ->//Citizen of ruined Earth
                    false;
            default -> false;
        };
        //for Alliance assume non-hostile
    }

    /**
     * helper function that returns true if Imperial Armadran would attack person "other"
     * @param other - other person's allegiance
     * @return true if hostile
     */
    private boolean imperialInteraction(String other){
        return switch (other) {
            case "Alliance" ->//the Alliance
                    true;
            case "Imperial" ->//imperial Armadran
                    false;
            case "Rebel" ->//Armadran rebellion
                    true;
            case "Insurgent" ->//North Armadran insurgency
                    true;
            case "Vlakian" ->//Vlakian
                    false;
            case "Terran" ->//Citizen of ruined Earth
                    false;
            default -> false;
        };
        //for imperial assume non-hostile
    }

    /**
     * helper function that returns true if Southern Armadran Legion would attack person "other"
     * @param other - other person's allegiance
     * @return true if hostile
     */
    private boolean rebelInteraction(String other){
        return switch (other) {
            case "Alliance" ->//the Alliance
                    false;
            case "Imperial" ->//imperial Armadran
                    true;
            case "Rebel" ->//Armadran rebellion
                    true;
            case "Insurgent" ->//North Armadran insurgency
                    false;
            case "Vlakian" ->//Vlakian
                    false;
            case "Terran" ->//Citizen of ruined Earth
                    false;
            default -> false;
        };
        //for rebel assume non-hostile
    }

    /**
     * helper function that returns true if Northern Armadran Insurgency would attack person "other"
     * @param other - other person's allegiance
     * @return true if hostile
     */
    private boolean insurgentInteraction(String other){
        return switch (other) {
            case "Alliance" ->//the Alliance
                    true;
            case "Imperial" ->//imperial Armadran
                    true;
            case "Rebel" ->//Armadran rebellion
                    true;
            case "Insurgent" ->//North Armadran insurgency
                    false;
            case "Vlakian" ->//Vlakian
                    true;
            case "Terran" ->//Citizen of ruined Earth
                    true;
            default -> true;
        };
        //for insurgency assume hostile
    }

    /**
     * helper function that returns true if Aflackia would attack person "other"
     * @param other - other person's allegiance
     * @return true if hostile
     */
    private boolean vlakianInteraction(String other){
        return switch (other) {
            case "Alliance" ->//the Alliance
                    true;
            case "Imperial" ->//imperial Armadran
                    false;
            case "Rebel" ->//Armadran rebellion
                    false;
            case "Insurgent" ->//North Armadran insurgency
                    false;
            case "Vlakian" ->//Vlakian
                    false;
            case "Terran" ->//Citizen of ruined Earth
                    false;
            default -> false;
        };
        //for Vlakian assume non-hostile
    }

    /**
     * helper function that returns true if Terran survivors would attack person "other"
     * @param other - other person's allegiance
     * @return true if hostile
     */
    private boolean terranInteraction(String other){
        return switch (other) {
            case "Alliance" ->//the Alliance
                    false;
            case "Imperial" ->//imperial Armadran
                    false;
            case "Rebel" ->//Armadran rebellion
                    false;
            case "Insurgent" ->//North Armadran insurgency
                    false;
            case "Vlakian" ->//Vlakian
                    false;
            case "Terran" ->//Citizen of ruined Earth
                    false;
            default -> true;
        };
        //for terran assume hostile
    }
}
