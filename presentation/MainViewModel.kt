package com.example.catequeseapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.catequeseapp.data.models.Arquivo
import com.example.catequeseapp.data.models.Feedback
import java.util.Date

class MainViewModel : ViewModel() {
    
    var arquivos by mutableStateOf(listOf<Arquivo>())
        private set
    
    var feedbacks by mutableStateOf(listOf<Feedback>())
        private set
    
    var showAddArquivoDialog by mutableStateOf(false)
        private set
    
    var showAddFeedbackDialog by mutableStateOf(false)
        private set
    
    private var nextArquivoId = 5
    private var nextFeedbackId = 5
    
    init {
        loadMockData()
    }
    
    private fun loadMockData() {
        arquivos = listOf(
            Arquivo(
                id = 1,
                titulo = "Plano de Aula - O Bom Samaritano",
                descricao = "Plano completo com dinâmicas e reflexões sobre a parábola",
                tipo = "PLANO_AULA",
                autor = "Professora Mariana",
                dataUpload = Date(),
                url = ""
            ),
            Arquivo(
                id = 2,
                titulo = "Slides - Os 10 Mandamentos",
                descricao = "Apresentação visual interativa para crianças",
                tipo = "SLIDE",
                autor = "Coordenador Carlos",
                dataUpload = Date(),
                url = ""
            ),
            Arquivo(
                id = 3,
                titulo = "Atividade para colorir - Páscoa",
                descricao = "Desenhos sobre a ressurreição para os catequizandos",
                tipo = "PDF",
                autor = "Professora Ana",
                dataUpload = Date(),
                url = ""
            ),
            Arquivo(
                id = 4,
                titulo = "Guia do Catequista 2024",
                descricao = "Manual com orientações e cronograma anual",
                tipo = "PDF",
                autor = "Coordenação",
                dataUpload = Date(),
                url = ""
            )
        )
        
        feedbacks = listOf(
            Feedback(
                id = 1,
                aulaId = 1,
                aulaTitulo = "O Bom Samaritano",
                autor = "Professor José",
                comentario = "A dinâmica proposta funcionou muito bem com as crianças!",
                avaliacao = 5,
                dataFeedback = Date(),
                sugestao = "Incluir mais músicas para a próxima aula"
            ),
            Feedback(
                id = 2,
                aulaId = 2,
                aulaTitulo = "Os 10 Mandamentos",
                autor = "Professora Lúcia",
                comentario = "Os slides são ótimos, mas alguns alunos acharam as imagens muito pequenas.",
                avaliacao = 4,
                dataFeedback = Date(),
                sugestao = "Ampliar as imagens nos próximos materiais"
            ),
            Feedback(
                id = 3,
                aulaId = null,
                aulaTitulo = "Oração do Pai Nosso",
                autor = "Catequista Roberto",
                comentario = "Excelente material de apoio para a oração.",
                avaliacao = 5,
                dataFeedback = Date(),
                sugestao = ""
            ),
            Feedback(
                id = 4,
                aulaId = null,
                aulaTitulo = "Sacramentos",
                autor = "Professora Paula",
                comentario = "Precisamos de mais exemplos práticos para as crianças.",
                avaliacao = 3,
                dataFeedback = Date(),
                sugestao = "Trazer exemplos do cotidiano"
            )
        )
    }
    
    fun toggleAddArquivoDialog() {
        showAddArquivoDialog = !showAddArquivoDialog
    }
    
    fun toggleAddFeedbackDialog() {
        showAddFeedbackDialog = !showAddFeedbackDialog
    }
    
    fun addArquivo(titulo: String, descricao: String, tipo: String, autor: String) {
        val novoArquivo = Arquivo(
            id = nextArquivoId,
            titulo = titulo,
            descricao = descricao,
            tipo = tipo,
            autor = autor.ifEmpty { "Professor(a)" },
            dataUpload = Date(),
            url = ""
        )
        arquivos = arquivos + novoArquivo
        nextArquivoId++
        showAddArquivoDialog = false
    }
    
    fun addFeedback(aulaTitulo: String, comentario: String, avaliacao: Int, sugestao: String, autor: String) {
        val novoFeedback = Feedback(
            id = nextFeedbackId,
            aulaId = null,
            aulaTitulo = aulaTitulo,
            autor = autor.ifEmpty { "Professor(a)" },
            comentario = comentario,
            avaliacao = avaliacao,
            dataFeedback = Date(),
            sugestao = sugestao
        )
        feedbacks = feedbacks + novoFeedback
        nextFeedbackId++
        showAddFeedbackDialog = false
    }
}
