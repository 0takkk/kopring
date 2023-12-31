package com.example.kopring.controller

import com.example.kopring.common.response.ResponseService
import com.example.kopring.common.result.ListResult
import com.example.kopring.common.result.Result
import com.example.kopring.common.result.SingleResult
import com.example.kopring.dto.board.BoardDto
import com.example.kopring.dto.board.BoardSearchDto
import com.example.kopring.service.BoardService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
class BoardController (
    private val boardService: BoardService,
    private val responseService: ResponseService
){
    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping
    fun writeBoard(@RequestBody boardDto: BoardDto) : Result{
        boardService.writeBoard(boardDto)
        return responseService.getSuccessResult()
    }

    @GetMapping("/{id}")
    fun searchBoardById(@PathVariable("id") boardId : Long) : SingleResult<BoardSearchDto>{
        val requestDto = boardService.searchBoardById(boardId)
        return responseService.getSingleResult(requestDto)
    }

    @GetMapping
    fun searchBoardListByFilter(
        @RequestParam(value = "title", required = false) title : String?,
        @RequestParam(value = "writer", required = false) writer : String?
    ) : ListResult<BoardSearchDto>{
        log.info("title = {}", title)
        val requestDto = boardService.searchBoardListByFilter(title, writer)
        return responseService.getListResult(requestDto)
    }

}