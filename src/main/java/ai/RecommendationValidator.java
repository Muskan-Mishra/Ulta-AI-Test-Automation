package ai;

import java.util.*;

public final class RecommendationValidator {
    private static final Set<String> BEAUTY=Set.of("beauty","skin","skincare","moisturizer","hydrating","cream","serum","vitamin c","makeup","mascara","lash","eye","face","foundation","concealer","lip","blush","bronzer","cleanser","toner","sunscreen","spf","retinol","peptide","hyaluronic","niacinamide","cosmetic");
    private RecommendationValidator(){}
    public static double relevance(List<String> recommendations,String... contexts){
        if(recommendations==null||recommendations.isEmpty()) return 0.0;
        int relevant=0; String ctx=String.join(" ",contexts).toLowerCase();
        for(String r:recommendations){String s=(r==null?"":r).toLowerCase(); boolean ok=BEAUTY.stream().anyMatch(s::contains) || Arrays.stream(contexts).anyMatch(c->s.contains(c.toLowerCase())); if(ctx.contains("mascara")&&s.contains("eye"))ok=true; if(ok)relevant++;}
        return (double)relevant/recommendations.size();
    }
    public static void assertThreshold(List<String> rec,double threshold,String... contexts){
        double score=relevance(rec,contexts); if(score<threshold) throw new AssertionError(String.format("Recommendation relevance %.2f%% is below required %.2f%%. Recommendations=%s",score*100,threshold*100,rec));
    }
}
