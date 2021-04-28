package suncodes.opensource.imports;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 通过全限定类名，
        // MyImportSelector 不会注入，如果需要获取，前面加 &
        return new String[]{"suncodes.opensource.imports.TestC"};
    }
}
