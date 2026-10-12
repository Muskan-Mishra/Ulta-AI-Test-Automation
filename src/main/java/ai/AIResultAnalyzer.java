package ai;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.regex.*;

public class AIResultAnalyzer {
    public static void main(String[] args) throws Exception {
        Path src=Paths.get(args.length>0?args[0]:"target/cucumber.json"); Path out=Paths.get(args.length>1?args[1]:"reports/ai-summary.md"); Files.createDirectories(out.getParent());
        if(!Files.exists(src)){Files.writeString(out,"# AI Test Execution Summary\n\nNo cucumber JSON found. Run `mvn clean test` first.\n");return;}
        String json=Files.readString(src); int pass=count(json,"\"status\"\\s*:\\s*\"passed\""), fail=count(json,"\"status\"\\s*:\\s*\"failed\""), skip=count(json,"\"status\"\\s*:\\s*\"skipped\""); int total=pass+fail+skip; double rate=total==0?0:100.0*pass/total;
        Map<String,Integer> clusters=new LinkedHashMap<>();
        add(clusters,"Synchronization / Timeout",json,"TimeoutException|timeout|stale element|ElementClickInterceptedException|intercepted");
        add(clusters,"Element / Locator",json,"NoSuchElementException|no such element|locator|Unable to locate");
        add(clusters,"Recommendation",json,"recommendation|30 percent|25 percent|20 percent");
        add(clusters,"Cart",json,"cart|bag|items|products");
        add(clusters,"Assertion / Business Rule",json,"AssertionError|assert|threshold");
        StringBuilder md=new StringBuilder(); md.append("# AI Test Execution Summary\n\nGenerated: ").append(Instant.now()).append("\n\n"); md.append("## Overall Result\n\n").append("|Metric|Value|\n|---|---:|\n|Total|"+total+"|\n|Passed|"+pass+"|\n|Failed|"+fail+"|\n|Skipped|"+skip+"|\n|Pass rate|"+String.format(Locale.US,"%.2f",rate)+"%|\n\n");
        md.append("## AI-Assisted Defect Clustering\n\n"); clusters.forEach((k,v)->{if(v>0)md.append("- **").append(k).append("**: ").append(v).append(" matching result(s).\n");});
        md.append("\n## Trend Interpretation\n\n"); if(rate>=90)md.append("Stable execution: pass rate is at least 90%. Continue monitoring flaky failures.\n"); else if(rate>=70)md.append("Moderate stability: investigate repeated failures before release.\n"); else md.append("High risk: failure rate is significant; prioritize clustered defects and rerun after fixes.\n");
        md.append("\n## Copilot Prompt for Root-Cause Analysis\n\n```text\nAnalyze the following failed test output. Cluster similar failures, identify probable root causes, distinguish product defects from automation/environment issues, and recommend fixes. Return a table with cluster, evidence, probable root cause, severity, and next action.\n\n").append(json.length()>12000?json.substring(0,12000):json).append("\n```\n"); Files.writeString(out,md.toString());
    }
    static int count(String s,String regex){Matcher m=Pattern.compile(regex,Pattern.CASE_INSENSITIVE).matcher(s);int n=0;while(m.find())n++;return n;}
    static void add(Map<String,Integer> m,String k,String json,String terms){int n=0;for(String t:terms.split("\\|"))n+=count(json,Pattern.quote(t));m.put(k,n);}
}
