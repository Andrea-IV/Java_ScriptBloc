package AnoParser;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.PrintWriter;
import java.util.Set;

@SupportedAnnotationTypes("AnoParser.MethodInfo")
@SupportedSourceVersion(SourceVersion.RELEASE_9)
public class AnoParser extends AbstractProcessor {

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            if(roundEnv.processingOver()){
                return false;
            }
            PrintWriter writer = new PrintWriter("C:\\Users\\Dell\\Desktop\\WorkspaceGit\\Java_ScriptBloc\\JAVASCRIPTBLOCKS\\out\\artifacts\\JavaScriptBlocks_jar\\DocBlock.txt");
            writer.println("                                   =========================================================");
            writer.println("                                   ||  SCRIPT::BLOCKS GUI PACKAGE FUNCTION DOCUMENTATION  ||");
            writer.println("                                   =========================================================");
            writer.println();
            for(TypeElement annotation: annotations) {
                for (Element rootElement : roundEnv.getRootElements()) {
                    writer.println("||----------CLASS----------");
                    writer.println("||     " + rootElement.toString());
                    writer.println("||-------------------------");
                    writer.println("||");
                    for (Element element : rootElement.getEnclosedElements()) {
                        if (element.getKind() == ElementKind.CONSTRUCTOR) {
                            if (element.getAnnotation(MethodInfo.class) != null) {
                                writer.println("||    ---------CONSTRUCTOR----------");
                                writer.println("||         "+element.getAnnotation(MethodInfo.class).name());
                                writer.println("||    -------------------------");
                                writer.println("||    -- DATE : " + element.getAnnotation(MethodInfo.class).date());
                                writer.println("||    -- REVISION : " + element.getAnnotation(MethodInfo.class).revision());
                                writer.println("||    -- DESCRIPTION : " + element.getAnnotation(MethodInfo.class).comments());
                                writer.println("||    -- ARGUMENTS : " + element.getAnnotation(MethodInfo.class).arguments());
                                writer.println("||    -- RETURN : " + element.getAnnotation(MethodInfo.class).returnValue());
                            }
                        }
                        if (element.getKind() == ElementKind.FIELD) {
                            if (element.getAnnotation(MethodInfo.class) != null) {
                                writer.println("||    ---------FIELD----------");
                                writer.println("||         "+element.getAnnotation(MethodInfo.class).name());
                                writer.println("||    -------------------------");
                                writer.println("||    -- DATE : " + element.getAnnotation(MethodInfo.class).date());
                                writer.println("||    -- DATE : " + element.getAnnotation(MethodInfo.class).date());
                                writer.println("||    -- REVISION : " + element.getAnnotation(MethodInfo.class).revision());
                                writer.println("||    -- DESCRIPTION : " + element.getAnnotation(MethodInfo.class).comments());
                                writer.println("||    -- ARGUMENTS : " + element.getAnnotation(MethodInfo.class).arguments());
                                writer.println("||    -- RETURN : " + element.getAnnotation(MethodInfo.class).returnValue());
                            }
                        }
                        if (element.getKind() == ElementKind.METHOD) {
                            if (element.getAnnotation(MethodInfo.class) != null) {
                                writer.println("||    ---------METHOD----------");
                                writer.println("||         "+element.getAnnotation(MethodInfo.class).name());
                                writer.println("||    -------------------------");
                                writer.println("||    -- DATE : " + element.getAnnotation(MethodInfo.class).date());
                                writer.println("||    -- REVISION : " + element.getAnnotation(MethodInfo.class).revision());
                                writer.println("||    -- DESCRIPTION : " + element.getAnnotation(MethodInfo.class).comments());
                                writer.println("||    -- ARGUMENTS : " + element.getAnnotation(MethodInfo.class).arguments());
                                writer.println("||    -- RETURN : " + element.getAnnotation(MethodInfo.class).returnValue());
                            }
                        }
                    }
                }
            }

            writer.close();
        }catch (Exception e){
            try {
                PrintWriter writer = new PrintWriter("C:\\Users\\Dell\\Desktop\\WorkspaceGit\\Java_ScriptBloc\\JAVASCRIPTBLOCKS\\out\\artifacts\\JavaScriptBlocks_jar\\Ano.txt");
                writer.println(e);
                writer.close();
            }catch (Exception ex){
                System.out.println(ex);
            }
        }
        return true;
    }
}
