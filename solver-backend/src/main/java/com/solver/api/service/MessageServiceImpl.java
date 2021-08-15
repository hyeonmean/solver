package com.solver.api.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.solver.api.request.MessageConferenceCreatePostReq;
import com.solver.api.response.MessageListRes;
import com.solver.common.auth.KakaoUtil;
import com.solver.common.model.TokenResponse;
import com.solver.common.util.RandomIdUtil;
import com.solver.db.entity.answer.Answer;
import com.solver.db.entity.code.Code;
import com.solver.db.entity.user.Message;
import com.solver.db.entity.user.User;
import com.solver.db.repository.answer.AnswerRepository;
import com.solver.db.repository.code.CodeRepository;
import com.solver.db.repository.user.MessageRepository;
import com.solver.db.repository.user.UserRepository;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserRepository UserRepository;
	
	@Autowired
	KakaoUtil kakaoUtil;
	
	@Autowired
	AnswerRepository answerRepository;
	
	@Autowired
	CodeRepository codeRepository;

	@Override
	public MessageListRes getSendMessageList(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);

		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = UserRepository.findByKakaoId(kakaoId).orElse(null);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		MessageListRes messageListRes = new MessageListRes();
		
		if(user == null) {
			messageListRes.setMessage("보낸 메시지 목록 조회 실패");
			messageListRes.setStatusCode(409);
			return messageListRes;
		}
		
		List<Message> messageList = messageRepository.findBySendUserId(user.getId());
		
		messageListRes.setMessageList(messageList);
		messageListRes.setMessage("보낸 메시지 목록 조회 성공");
		messageListRes.setStatusCode(200);
		
		return messageListRes;
	}
	
	@Override
	public MessageListRes getReceiveMessageList(String accessToken, HttpServletResponse response) {
		String token = accessToken.split(" ")[1];
		
		TokenResponse tokenResponse = new TokenResponse();
		
		tokenResponse = kakaoUtil.getKakaoUserIdByToken(token);
		
		Long kakaoId = tokenResponse.getKakaoId();
		
		User user = UserRepository.findByKakaoId(kakaoId).orElse(null);
		
		if(tokenResponse.getAccessToken() != null) {
			response.setHeader("Authorization", tokenResponse.getAccessToken());
		}
		
		MessageListRes messageListRes = new MessageListRes();
		
		if(user == null) {
			messageListRes.setMessage("받은 메시지 목록 조회 실패");
			messageListRes.setStatusCode(409);
			return messageListRes;
		}
		
		List<Message> messageList = messageRepository.findByReceiveUserId(user.getId());
		
		messageListRes.setMessageList(messageList);
		messageListRes.setMessage("받은 메시지 목록 조회 성공");
		messageListRes.setStatusCode(200);
		
		return messageListRes;
	}

	@Override
	public void insertMessage(MessageConferenceCreatePostReq messagePostReq) {
		//System.out.println(messagePostReq.getAnswerId());
		
		Optional<Answer> answer = answerRepository.findById(messagePostReq.getAnswerId());
		
		if (answer.orElse(null) != null) {
			Message message = new Message();
			message.setId(RandomIdUtil.makeRandomId(13));
			message.setSendUser(answer.get().getUser());
			message.setReceiveUser(answer.get().getQuestion().getUser());
			message.setQuestionId(answer.get().getQuestion().getId());
			Code code = codeRepository.findByCode(messagePostReq.getType());
			message.setCode(code);
			
			String content = "";
			if(code.getCode().equals("072")) {				
				// 화상요청의 내용은 시간
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date to = null;
				String time = null;
				try {
					to = transFormat.parse(messagePostReq.getRegDt());
					time = transFormat.format(to);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				content = time;
			}
			
			message.setContent(content);
			messageRepository.save(message);
		}
	}
}