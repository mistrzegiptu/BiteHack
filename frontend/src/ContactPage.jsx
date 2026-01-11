import React, { useState } from 'react';
import './Contact.css';

const ContactPage = () => {
  // Stan przechowujący indeks otwartego pytania (null = wszystkie zamknięte)
  const [openIndex, setOpenIndex] = useState(null);

  const toggleFAQ = (index) => {
    setOpenIndex(openIndex === index ? null : index);
  };

  const faqData = [
    {
      question: "Jaki jest czas realizacji zamówienia?",
      answer: "Standardowy czas wysyłki to 24-48 godzin. Dostawa kurierem zajmuje zazwyczaj 1 dzień roboczy od momentu nadania paczki."
    },
    {
      question: "Czy mogę zwrócić zakupiony towar?",
      answer: "Tak, masz 30 dni na zwrot towaru bez podania przyczyny. Produkt nie może nosić śladów użytkowania."
    },
    {
      question: "Jakie są dostępne metody płatności?",
      answer: "Obsługujemy szybkie płatności online (BLIK, przelewy natychmiastowe), karty płatnicze oraz płatność przy odbiorze."
    },
    {
      question: "Czy posiadacie sklep stacjonarny?",
      answer: "Tak, nasz główny showroom znajduje się w Krakowie przy ulicy Rzemieślniczej 10. Zapraszamy od poniedziałku do piątku w godzinach 9-17."
    },
    {
      question: "Czy wysyłacie paczki za granicę?",
      answer: "Obecnie realizujemy wysyłki tylko na terenie Polski. Pracujemy nad rozszerzeniem oferty o kraje UE."
    },
    {
      question: "Jak mogę złożyć reklamację?",
      answer: "Reklamację można złożyć mailowo na adres reklamacje@lokalnybiznes.pl lub poprzez formularz w panelu klienta."
    },
    {
      question: "Czy muszę zakładać konto, żeby zrobić zakupy?",
      answer: "Nie, zakupy można zrobić jako gość. Jednak założenie konta pozwala na śledzenie statusu zamówienia i zbieranie punktów lojalnościowych."
    },
    {
      question: "Jak dobrać odpowiedni rozmiar?",
      answer: "Przy każdym produkcie znajduje się tabela rozmiarów. Zalecamy zmierzenie swojej ulubionej odzieży i porównanie wymiarów."
    },
    {
      question: "Gdzie znajdę kod rabatowy?",
      answer: "Kody rabatowe udostępniamy w naszym newsletterze oraz na social mediach. Aktualny kod dla uczestników hackathonu to BITEHACK2026."
    },
    {
      question: "Czy mogę anulować zamówienie?",
      answer: "Tak, zamówienie można anulować do momentu otrzymania maila o przekazaniu paczki do wysyłki. Prosimy o szybki kontakt telefoniczny."
    }
  ];

  return (
    <div className="contact-container">
      
      {/* SEKCJA DANYCH KONTAKTOWYCH */}
      <div className="contact-header">
        <h1>Skontaktuj się z nami</h1>
        <p>Jesteśmy do Twojej dyspozycji w dni robocze od 8:00 do 16:00.</p>
        
        <div className="contact-info-cards">
          <div className="info-card">
            <h3>Infolinia</h3>
            <p className="highlight">+48 123 456 789</p>
            <small>Opłata wg taryfy operatora</small>
          </div>
          <div className="info-card">
            <h3>Email</h3>
            <p className="highlight">kontakt@lokalnybiznes.pl</p>
            <small>Odpowiadamy w ciągu 24h</small>
          </div>
          <div className="info-card">
            <h3>Adres</h3>
            <p>ul. Rzemieślnicza 10</p>
            <p>30-001 Kraków</p>
          </div>
        </div>
      </div>

      {/* SEKCJA FAQ */}
      <div className="faq-section">
        <h2>Najczęściej zadawane pytania (FAQ)</h2>
        
        <div className="faq-list">
          {faqData.map((item, index) => (
            <div 
              key={index} 
              className={`faq-item ${openIndex === index ? 'open' : ''}`}
              onClick={() => toggleFAQ(index)}
            >
              <div className="faq-question">
                <span>{item.question}</span>
                <span className="faq-icon">{openIndex === index ? '−' : '+'}</span>
              </div>
              
              {openIndex === index && (
                <div className="faq-answer">
                  <p>{item.answer}</p>
                </div>
              )}
            </div>
          ))}
        </div>
      </div>

    </div>
  );
};

export default ContactPage;