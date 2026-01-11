import { useState } from 'react';
import { GoogleGenerativeAI } from "@google/generative-ai";
import './GeminiChat.css'; // Importujemy style

function GeminiChat() {
  const [prompt, setPrompt] = useState("");
  const [response, setResponse] = useState("");
  const [loading, setLoading] = useState(false);
  console.log("Wszystkie zmienne ENV:", import.meta.env);
    console.log("Mój klucz API:", import.meta.env.VITE_GEMINI_API_KEY);
  const genAI = new GoogleGenerativeAI(import.meta.env.VITE_GEMINI_API_KEY);
  const model = genAI.getGenerativeModel({ model: "gemini-2.5-flash" });

  const handleAskGemini = async () => {
    if (!prompt) return;
    
    setLoading(true);
    try {
      const result = await model.generateContent(prompt);
      const text = result.response.text();
      setResponse(text);
      setPrompt(""); // Czyścimy pole po wysłaniu
    } catch (error) {
      console.error("Błąd Gemini:", error);
      setResponse("Wystąpił błąd podczas generowania odpowiedzi.");
    }
    setLoading(false);
  };

  return (
    <div className="chat-container">
      <div className="chat-header">
        Asystent Gemini
      </div>
      
      <div className="chat-response-area">
        {response ? (
          <div>{response}</div>
        ) : (
          <span style={{ color: '#999' }}>W czym mogę Ci dzisiaj pomóc?</span>
        )}
      </div>

      <div className="chat-footer">
        <textarea 
          className="chat-input"
          rows="2"
          value={prompt} 
          onChange={(e) => setPrompt(e.target.value)}
          placeholder="Napisz wiadomość..."
        />
        <button 
          className="chat-button" 
          onClick={handleAskGemini} 
          disabled={loading || !prompt}
        >
          {loading ? "Generowanie..." : "Wyślij"}
        </button>
      </div>
    </div>
  );
}

export default GeminiChat;