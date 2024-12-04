import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { ChatInput } from './components/ChatInput';
import { ChatHistory } from './components/ChatHistory';
import { ChatMessages } from './components/ChatMessages';
import { PromptGrid } from "./components/PromptGrid";

export default function Chatbot() {
    const [messages, setMessages] = useState([]); // Không có tin nhắn khởi tạo
    const [chatHistory, setChatHistory] = useState([]);
    const [input, setInput] = useState("");
    const [activeChatId, setActiveChatId] = useState(null);
    const navigate = useNavigate();

    const handleSend = () => {
        if (input.trim() === "") return;

        const newUserMessage = {
            id: messages.length + 1,
            text: input,
            sender: "user",
        };

        const newBotMessage = {
            id: messages.length + 2,
            text: `Bạn vừa nói: "${input}". Tôi đang xử lý...`,
            sender: "bot",
        };

        const updatedMessages = [...messages, newUserMessage, newBotMessage];
        setMessages(updatedMessages);

        // Save current chat to history
        if (!activeChatId) {
            const newChatId = Date.now();
            setChatHistory(prev => [
                { 
                    id: newChatId, 
                    messages: updatedMessages, 
                    timestamp: new Date().toLocaleString() 
                },
                ...prev
            ]);
            setActiveChatId(newChatId);
        } else {
            setChatHistory(prev => prev.map(chat => 
                chat.id === activeChatId 
                    ? { ...chat, messages: updatedMessages } 
                    : chat
            ));
        }

        setInput("");
    };

    const loadChatHistory = (chatId) => {
        const selectedChat = chatHistory.find(chat => chat.id === chatId);
        if (selectedChat) {
            setMessages(selectedChat.messages);
            setActiveChatId(chatId);
        }
    };

    const handleNewChat = () => {
        const initialMessages = []; // Không khởi tạo tin nhắn ban đầu
        setMessages(initialMessages);
        setActiveChatId(null);
        setInput("");
    };

    return (
        <div className="flex h-screen">
            <ChatHistory 
                chatHistory={chatHistory}
                activeChatId={activeChatId}
                onLoadChat={loadChatHistory}
                onNewChat={handleNewChat}
                onGoHome={() => navigate("/")}
            /> 
            <div className="flex flex-col w-3/4 bg-gray-100">
                <ChatMessages messages={messages} />
                <PromptGrid />
                <ChatInput 
                    input={input}
                    onInputChange={(e) => setInput(e.target.value)}
                    onSend={handleSend}
                />
            </div>
        </div>
    );
}
