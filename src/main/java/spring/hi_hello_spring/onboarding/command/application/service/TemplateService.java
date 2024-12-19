package spring.hi_hello_spring.onboarding.command.application.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.hi_hello_spring.common.exception.CustomException;
import spring.hi_hello_spring.common.exception.ErrorCodeType;
import spring.hi_hello_spring.onboarding.command.application.dto.TeamplateOrderUpdateDTO;
import spring.hi_hello_spring.onboarding.command.application.dto.TemplateCreateDTO;
import spring.hi_hello_spring.onboarding.command.domain.aggregate.entity.Template;
import spring.hi_hello_spring.onboarding.command.domain.repository.TemplateRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    private final ModelMapper modelMapper;

    /* 온보딩 스토리 보드 등록 */
    @Transactional
    public TemplateCreateDTO createTemplate(TemplateCreateDTO createDTO){

        Template template = modelMapper.map(createDTO, Template.class);
        templateRepository.save(template);
        return createDTO;
    }

    /* 온보딩 스토리 보드 삭제*/
    @Transactional
    public boolean deleteTemplate(Long templateSeq){

        if(templateRepository.existsById(templateSeq)){
            templateRepository.deleteById(templateSeq);
            return true;
        }else {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }

    /* 온보딩 스토리 보드 순서 변경 */
    @Transactional
    public void updateOrderTemplate(List<TeamplateOrderUpdateDTO> updateDTOs) {
        // 요청받은 각 템플릿 순서를 처리
        for (TeamplateOrderUpdateDTO dto : updateDTOs) {
            Optional<Template> templateOpt = templateRepository.findById(dto.getTemplateSeq()); // templateSeq로 템플릿 조회

            if (templateOpt.isPresent()) {
                Template template = templateOpt.get();

                // ModelMapper를 사용해서 TeamplateOrderUpdateDTO를 Template 엔티티로 매핑
                modelMapper.map(dto, template);

                // 만약 필요한 다른 매핑 항목이 있다면 여기에 설정 가능
                // 예: template.setTemplateProcedure(dto.getTemplateProcedure());

                // 템플릿 저장
                templateRepository.save(template);  // 변경된 템플릿 정보 저장
            }
        }
    }
}
