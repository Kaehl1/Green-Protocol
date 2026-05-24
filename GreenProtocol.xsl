<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <head>
                <link rel="stylesheet" href="./estilos.css" />
            </head>
            <body>
                <div class="historial">
                    <h2 class="titulo-historial">Historial de <xsl:value-of select="perfil/usuario"/></h2>
                    <div class="resumen">
                        <p>Total partidas de <xsl:value-of select="perfil/usuario"/>: <xsl:value-of select="count(perfil/juego/partidas/partida)"/></p>
                        <p>Total puntos: <xsl:value-of select="sum(perfil/juego/partidas/partida/puntos)"/></p>
                    </div>
                    <xsl:for-each select="perfil/juego/partidas/partida">
                        <div class="partida">
                            <h3>Partida <xsl:value-of select="@id"/></h3>
                            <ul>
                                <li>Héroe: <xsl:value-of select="nombreHeroe"/></li>
                                <li>Clase jugada: <xsl:value-of select="clase"/></li>
                                <li>
                                    Jefe <xsl:value-of select="jefe/nombre"/> <br/>
                                    Clase: <xsl:value-of select="jefe/clase"/>
                                </li>
                                <li>Puntos: <xsl:value-of select="puntos"/></li>
                            </ul>
                        </div>
                    </xsl:for-each>
                </div>
                <div class="logros">
                    <h2 class="titulo-logros">Logros de <xsl:value-of select="perfil/usuario"/></h2>
                    <div class="resumen">
                        <p>Puntos de <xsl:value-of select="perfil/usuario"/>: <xsl:value-of select="sum(perfil/juego/logros/logro[@completado='si']/@puntos)"/></p>
                        <p>Conseguidos: <xsl:value-of select="count(perfil/juego/logros/logro[@completado='si'])"/>/<xsl:value-of select="count(perfil/juego/logros/logro)"/></p>
                    </div>
                    <table>
                        <tr>
                            <th>Logro</th>
                            <th>Descripción</th>
                            <th>Puntos</th>
                            <th>Conseguido</th>
                        </tr>
                        <xsl:for-each select="perfil/juego/logros/logro">
                            <xsl:choose>
                                <xsl:when test="@secreto = 'no'">
                                    <tr>
                                        <td class="nombre-logro"><xsl:value-of select="nombre"/></td>
                                        <td><xsl:value-of select="descripcion"/></td>
                                        <td><xsl:value-of select="@puntos"/></td>
                                        <td>
                                            <xsl:choose>
                                                <xsl:when test="@completado = 'si'">Si</xsl:when>
                                                <xsl:otherwise>No</xsl:otherwise>
                                            </xsl:choose>
                                        </td>
                                    </tr>
                                </xsl:when>
                                <xsl:otherwise>
                                    <xsl:choose>
                                        <xsl:when test="@completado = 'si'">
                                            <tr>
                                                <td class="nombre-logro"><xsl:value-of select="nombre"/></td>
                                                <td><xsl:value-of select="descripcion"/></td>
                                                <td><xsl:value-of select="@puntos"/></td>
                                                <td>Si</td>
                                            </tr>
                                        </xsl:when>
                                        <xsl:otherwise>
                                            <tr>
                                                <td>?</td>
                                                <td>Logro Oculto</td>
                                                <td>?</td>
                                                <td>No</td>
                                            </tr>
                                        </xsl:otherwise>
                                    </xsl:choose>
                                </xsl:otherwise>
                            </xsl:choose>
                        </xsl:for-each>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>