package com.cit.restapi.rfidpanel;

import com.cit.clonedetection.om.CloneDetectionResult;
import com.cit.clonedetection.service.ICloneDetectionService;
import com.cit.common.om.access.device.RfidReaderPanel;
import com.cit.common.om.access.request.AccessRequest;
import com.cit.common.om.access.token.RfidBadge;
import com.cit.restapi.rfidpanel.dto.CloneDetectionResultDto;
import com.cit.restapi.rfidpanel.dto.RfidPanelAccessRequestDto;
import com.cit.restapi.rfidpanel.mapper.CloneDetectionResultMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Api(value = "api/panels/request", description = "RFID panel requests")
@RestController
@RequestMapping(value = "api/panels", produces = "application/json")
public class RfidPanelResource {

    @Autowired
    ICloneDetectionService cloneDetectionService;

    @Autowired
    CloneDetectionResultMapper cloneDetectionResultMapper;

    @ApiOperation(value="Validate whether RFID card was cloned", response = CloneDetectionResultDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "panelId", required = true, value = "unique identifier for RFID panel", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "cardId", required = true, value = "unique identifier for RFID card", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "allowed", required = true, value = "whether access is granted", paramType = "query", dataType = "boolean")
    })
    @RequestMapping(value = "/request", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<CloneDetectionResultDto> getValidation(@ModelAttribute @Valid RfidPanelAccessRequestDto requestDto)
    {
        AccessRequest<RfidBadge, RfidReaderPanel> accessRequest = cloneDetectionResultMapper.dtoToDomain(requestDto);
        CloneDetectionResult cloneValidationResult = cloneDetectionService.checkForClonedCard(accessRequest);
        CloneDetectionResultDto cloneDetectionResultDto = cloneDetectionResultMapper.domainToDto(cloneValidationResult);

        return new ResponseEntity<CloneDetectionResultDto>(cloneDetectionResultDto, HttpStatus.OK);
    }
}
