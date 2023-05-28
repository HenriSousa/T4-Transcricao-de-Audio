import './App.css';
import React, { useState } from 'react';
import axios from "axios";

function App() {
  const [translationText, setTranslationText] = useState("")
  const [translatedText, setTranslatedText] = useState("")
  const [file, setFile] = useState(null)
  const [transcriptionText, setTranscriptionText] = useState("")
  const [choiceIdioma, setChoiceIdioma] = useState(null)

  const handleOnChange = (e) => {
    setTranslationText(e.target.value)
  }

  const handleOnChangeFile = (e) => {
    setFile(e.target.files[0])
  }

  const handleOnChangeIdioma = (e) => {
    setChoiceIdioma(e.target.value)
  }

  const handleOnSubmitFile = () => {
    const formData = new FormData();
    formData.append("file", file)
    axios.post(`http://localhost:8080/upload/arquivo/${choiceIdioma}`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
    .then(result => {
      const data = JSON.parse(JSON.stringify(result.data))
      console.log(data)
      setTranscriptionText(JSON.parse(JSON.stringify(result.data)).traduction) 
   })
}  

  const handleOnSubmit = () => {
       console.log(translationText)
       axios.get(`http://localhost:8080/idioma/${translationText}/${choiceIdioma}`,)
       .then(result => {
        console.log(result.data)
        setTranslatedText(result.data)
      })
  }
  return (
    <div className="all">
    <div className="App">
      <header className="App-header">
        <div className="boxText">
        <div className="boxIdiomaTranslate">
        <label  for="exampleFormControlTextarea1" className="form-label"> <font size="5"> Digite o texto </font></label>
        <select onChange={handleOnChangeIdioma} className="form-select" aria-label="Default select example">
          <option selected>Selecione o Idioma</option>
          <option value="Pt">Português</option>
          <option value="En">Inglês</option>
          <option value="Fr">Francês</option>
          <option value="Es">Espanhol</option>
          <option value="It">Italiano</option>
        </select>        
        <textarea value={translationText} onChange={handleOnChange} className="form-control1 test" id="exampleFormControlTextarea1" rows="3"></textarea>
        <p className="boxResponse">{translatedText}</p>
        <div className="buttonTraduzir"><button onClick={handleOnSubmit} type="button" className="btn btn-info">Traduzir</button></div></div>
        </div>
        
        <div className="mb-3">
          <div className="boxUpload">
          <label for="formFile" className="form-label"> <font size="5"> Upload .wav </font></label>
          <input onChange={handleOnChangeFile} className="form-control" type="file" id="formFile"/>
          <p className="boxResponse">{transcriptionText}</p>
          <button onClick={handleOnSubmitFile} type="button" className="btn btn-info">Transcrição</button>
          </div>
        </div>
      </header>
    </div>
    </div>
  );
}

export default App;
