package com.example.catequeseapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Slideshow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.catequeseapp.data.models.Arquivo
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ArquivosScreen(
    arquivos: List<Arquivo>,
    onAddArquivoClick: () -> Unit,
    onArquivoAdd: (String, String, String, String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(Color(0xFF1B5E20), Color(0xFF2E7D32))
                    )
                )
                .padding(horizontal = 16.dp, vertical = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "📚 Biblioteca de Materiais",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "${arquivos.size} arquivos disponíveis",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
                
                Button(
                    onClick = { showDialog = true },
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA726)
                    )
                ) {
                    Icon(Icons.Default.Description, contentDescription = "Adicionar", tint = Color.White)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Novo Material", color = Color.White)
                }
            }
        }
        
        // Lista de arquivos
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(arquivos) { arquivo ->
                ArquivoCard(arquivo = arquivo)
            }
        }
    }
    
    // Dialog para adicionar novo arquivo
    if (showDialog) {
        AddArquivoDialog(
            onDismiss = { showDialog = false },
            onConfirm = { titulo, descricao, tipo, autor ->
                onArquivoAdd(titulo, descricao, tipo, autor)
                showDialog = false
            }
        )
    }
}

@Composable
fun ArquivoCard(arquivo: Arquivo) {
    val icon = when (arquivo.tipo) {
        "PDF" -> Icons.Default.PictureAsPdf
        "SLIDE" -> Icons.Default.Slideshow
        "PLANO_AULA" -> Icons.Default.MenuBook
        else -> Icons.Default.Description
    }
    
    val iconColor = when (arquivo.tipo) {
        "PDF" -> Color(0xFFE53935)
        "SLIDE" -> Color(0xFF43A047)
        "PLANO_AULA" -> Color(0xFF1E88E5)
        else -> Color(0xFF8D6E63)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(iconColor.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = arquivo.titulo,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = "📄 ${arquivo.tipo.replace("_", " ")} • 👤 ${arquivo.autor}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
                
                IconButton(onClick = { /* Simular download */ }) {
                    Icon(Icons.Default.Download, contentDescription = "Download", tint = Color(0xFF2E7D32))
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = arquivo.descricao,
                fontSize = 13.sp,
                color = Color(0xFF666666),
                maxLines = 2
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "📅 ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(arquivo.dataUpload)}",
                fontSize = 10.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun AddArquivoDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, String) -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("PDF") }
    var autor by remember { mutableStateOf("") }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "➕ Novo Material",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título do Material") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E7D32),
                        focusedLabelColor = Color(0xFF2E7D32)
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E7D32),
                        focusedLabelColor = Color(0xFF2E7D32)
                    )
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Seletor de tipo simplificado
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("PDF", "SLIDE", "PLANO_AULA").forEach { tipoOpcao ->
                        Button(
                            onClick = { tipo = tipoOpcao },
                            shape = RoundedCornerShape(20.dp),
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = if (tipo == tipoOpcao) Color(0xFF2E7D32) else Color(0xFFE0E0E0)
                            ),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                when (tipoOpcao) {
                                    "PDF" -> "📄 PDF"
                                    "SLIDE" -> "📽️ Slide"
                                    else -> "📖 Plano"
                                },
                                color = if (tipo == tipoOpcao) Color.White else Color.Gray
                            )
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                OutlinedTextField(
                    value = autor,
                    onValueChange = { autor = it },
                    label = { Text("Seu Nome (opcional)") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF2E7D32),
                        focusedLabelColor = Color(0xFF2E7D32)
                    )
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBDBDBD)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancelar", color = Color.White)
                    }
                    
                    Button(
                        onClick = { onConfirm(titulo, descricao, tipo, autor) },
                        enabled = titulo.isNotBlank(),
                        shape = RoundedCornerShape(12.dp),
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E7D32)
                        ),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Publicar", color = Color.White)
                    }
                }
            }
        }
    }
}
