package az.pashabank.dp.ms.qa.employee.controller


import az.pashabank.dp.ms.qa.employee.model.LabelDto
import az.pashabank.dp.ms.qa.employee.model.PositionDto
import az.pashabank.dp.ms.qa.employee.service.LabelService
import az.pashabank.dp.ms.qa.employee.service.PositionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/qa-employee/positions")
class PositionController(private val positionService: PositionService) {

    @GetMapping
    fun getPositions() = positionService.getPositions()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createPositions(@RequestBody position: PositionDto) = positionService.createPosition(position)

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun editPosition(@PathVariable id: Long, @RequestBody position: PositionDto) = positionService.editPosition(id, position)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deletePosition(@PathVariable id: Long) = positionService.deletePosition(id)

}