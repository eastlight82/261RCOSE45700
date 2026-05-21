package unflatten.demo.avatar.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unflatten.demo.avatar.service.TemplateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/templates")
public class TemplateController {

    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public List<ApiDtos.TemplateResponse> listTemplates() {
        return templateService.listTemplates();
    }

    @GetMapping("/{templateId}")
    public ApiDtos.TemplateResponse getTemplate(@PathVariable String templateId) {
        return templateService.getTemplate(templateId);
    }
}
