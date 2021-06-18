package com.loctek.workflow.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface JobGroupList {
    List<String> group1 = Arrays.asList("销售族群", "市场族群", "产品研发族群", "技术族群", "生产供应链族群");
    List<String> group2 = Arrays.asList("战略运营族群", "人力资源族群", "财务族群", "风控族群", "综合族群");
    List<String> group3 = Collections.singletonList("操作族群");
    List<String> group4 = Collections.singletonList("管理族群");
}
