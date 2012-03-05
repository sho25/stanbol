begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|///*
end_comment

begin_comment
comment|// * Licensed to the Apache Software Foundation (ASF) under one or more
end_comment

begin_comment
comment|// * contributor license agreements.  See the NOTICE file distributed with
end_comment

begin_comment
comment|// * this work for additional information regarding copyright ownership.
end_comment

begin_comment
comment|// * The ASF licenses this file to You under the Apache License, Version 2.0
end_comment

begin_comment
comment|// * (the "License"); you may not use this file except in compliance with
end_comment

begin_comment
comment|// * the License.  You may obtain a copy of the License at
end_comment

begin_comment
comment|// *
end_comment

begin_comment
comment|// *     http://www.apache.org/licenses/LICENSE-2.0
end_comment

begin_comment
comment|// *
end_comment

begin_comment
comment|// * Unless required by applicable law or agreed to in writing, software
end_comment

begin_comment
comment|// * distributed under the License is distributed on an "AS IS" BASIS,
end_comment

begin_comment
comment|// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
end_comment

begin_comment
comment|// * See the License for the specific language governing permissions and
end_comment

begin_comment
comment|// * limitations under the License.
end_comment

begin_comment
comment|// */
end_comment

begin_comment
comment|//package org.apache.stanbol.contenthub.ldpath.backend.clerezza;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import java.io.File;
end_comment

begin_comment
comment|//import java.io.FileInputStream;
end_comment

begin_comment
comment|//import java.io.FileNotFoundException;
end_comment

begin_comment
comment|//import java.io.FileReader;
end_comment

begin_comment
comment|//import java.util.Collection;
end_comment

begin_comment
comment|//import java.util.Iterator;
end_comment

begin_comment
comment|//import java.util.Map;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import org.apache.clerezza.rdf.core.MGraph;
end_comment

begin_comment
comment|//import org.apache.clerezza.rdf.core.Resource;
end_comment

begin_comment
comment|//import org.apache.clerezza.rdf.core.impl.SimpleMGraph;
end_comment

begin_comment
comment|//import org.apache.clerezza.rdf.core.serializedform.Parser;
end_comment

begin_comment
comment|//import org.apache.clerezza.rdf.core.serializedform.SupportedFormat;
end_comment

begin_comment
comment|//import org.apache.clerezza.rdf.jena.parser.JenaParserProvider;
end_comment

begin_comment
comment|//import org.apache.commons.cli.CommandLine;
end_comment

begin_comment
comment|//import org.apache.commons.cli.CommandLineParser;
end_comment

begin_comment
comment|//import org.apache.commons.cli.HelpFormatter;
end_comment

begin_comment
comment|//import org.apache.commons.cli.Option;
end_comment

begin_comment
comment|//import org.apache.commons.cli.OptionBuilder;
end_comment

begin_comment
comment|//import org.apache.commons.cli.OptionGroup;
end_comment

begin_comment
comment|//import org.apache.commons.cli.Options;
end_comment

begin_comment
comment|//import org.apache.commons.cli.ParseException;
end_comment

begin_comment
comment|//import org.apache.commons.cli.PosixParser;
end_comment

begin_comment
comment|//import org.apache.stanbol.commons.ldpath.clerezza.ClerezzaBackend;
end_comment

begin_comment
comment|//import org.slf4j.Logger;
end_comment

begin_comment
comment|//import org.slf4j.LoggerFactory;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import at.newmedialab.ldpath.LDPath;
end_comment

begin_comment
comment|//import at.newmedialab.ldpath.api.backend.RDFBackend;
end_comment

begin_comment
comment|//import at.newmedialab.ldpath.exception.LDPathParseException;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|///**
end_comment

begin_comment
comment|// * This class provides a main function for executing an LDProgram on {@link ClerezzaBackend} ,which is
end_comment

begin_comment
comment|// * Clerezza based implementation of {@link RDFBackend}, populated with RDF data.
end_comment

begin_comment
comment|// *
end_comment

begin_comment
comment|// * @author suat
end_comment

begin_comment
comment|// *
end_comment

begin_comment
comment|// */
end_comment

begin_comment
comment|//public class ClerezzaQuery {
end_comment

begin_comment
comment|//    private static final Logger logger = LoggerFactory.getLogger(ClerezzaQuery.class);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static Parser clerezzaRDFParser;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * This executable method provides execution of an LDPath program over an RDF data and prints out the
end_comment

begin_comment
comment|//     * obtained results. Passed RDF data should be in<b>RDF/XML</b> format.<br>
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * Usage of this main method is as follows:<br>
end_comment

begin_comment
comment|//     * ClerezzaQuery -context<pre>&lt;uri></pre> -filePath<pre>&lt;filePath></pre> -path<pre>&lt;path></pre> | -program<pre>&lt;file></pre></br></br>
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     *<b><code>context</code>:</b> URI of the context node to start from<br>
end_comment

begin_comment
comment|//     *<b><code>rdfData</code>:</b> File system path of the file holding RDF data<br>
end_comment

begin_comment
comment|//     *<b><code>path</code>:</b> LD Path to evaluate on the file starting from the<code>context</code><br>
end_comment

begin_comment
comment|//     *<b><code>program</code>:</b> LD Path program to evaluate on the file starting from the
end_comment

begin_comment
comment|//     *<code>context</code><br>
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param args
end_comment

begin_comment
comment|//     *            Collection of<code>context</code>,<code>rdfData</code>,<code>path</code> and<code>program</code> parameters
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    public static void main(String[] args) {
end_comment

begin_comment
comment|//        Options options = buildOptions();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        CommandLineParser parser = new PosixParser();
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            CommandLine cmd = parser.parse(options, args);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            ClerezzaBackend clerezzaBackend = null;
end_comment

begin_comment
comment|//            if (cmd.hasOption("rdfData")) {
end_comment

begin_comment
comment|//                clerezzaRDFParser = new Parser();
end_comment

begin_comment
comment|//                clerezzaRDFParser.bindParsingProvider(new JenaParserProvider());
end_comment

begin_comment
comment|//                MGraph mGraph = new SimpleMGraph(clerezzaRDFParser.parse(
end_comment

begin_comment
comment|//                    new FileInputStream(cmd.getOptionValue("rdfData")), SupportedFormat.RDF_XML));
end_comment

begin_comment
comment|//                clerezzaBackend = new ClerezzaBackend(mGraph);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            Resource context = null;
end_comment

begin_comment
comment|//            if (cmd.hasOption("context")) {
end_comment

begin_comment
comment|//                context = clerezzaBackend.createURI(cmd.getOptionValue("context"));
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            if (clerezzaBackend != null&& context != null) {
end_comment

begin_comment
comment|//                LDPath<Resource> ldpath = new LDPath<Resource>(clerezzaBackend);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                if (cmd.hasOption("path")) {
end_comment

begin_comment
comment|//                    String path = cmd.getOptionValue("path");
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    for (Resource r : ldpath.pathQuery(context, path, null)) {
end_comment

begin_comment
comment|//                        System.out.println(r.toString());
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                } else if (cmd.hasOption("program")) {
end_comment

begin_comment
comment|//                    File file = new File(cmd.getOptionValue("program"));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    Map<String,Collection<?>> result = ldpath.programQuery(context, new FileReader(file));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    for (String field : result.keySet()) {
end_comment

begin_comment
comment|//                        StringBuilder line = new StringBuilder();
end_comment

begin_comment
comment|//                        line.append(field);
end_comment

begin_comment
comment|//                        line.append(" = ");
end_comment

begin_comment
comment|//                        line.append("{");
end_comment

begin_comment
comment|//                        for (Iterator<?> it = result.get(field).iterator(); it.hasNext();) {
end_comment

begin_comment
comment|//                            line.append(it.next().toString());
end_comment

begin_comment
comment|//                            if (it.hasNext()) {
end_comment

begin_comment
comment|//                                line.append(", ");
end_comment

begin_comment
comment|//                            }
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//                        line.append("}");
end_comment

begin_comment
comment|//                        System.out.println(line);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        } catch (ParseException e) {
end_comment

begin_comment
comment|//            logger.error("invalid arguments");
end_comment

begin_comment
comment|//            HelpFormatter formatter = new HelpFormatter();
end_comment

begin_comment
comment|//            formatter.printHelp("ClerezzaQuery", options, true);
end_comment

begin_comment
comment|//        } catch (LDPathParseException e) {
end_comment

begin_comment
comment|//            logger.error("path or program could not be parsed");
end_comment

begin_comment
comment|//            e.printStackTrace();
end_comment

begin_comment
comment|//        } catch (FileNotFoundException e) {
end_comment

begin_comment
comment|//            logger.error("file or program could not be found");
end_comment

begin_comment
comment|//            HelpFormatter formatter = new HelpFormatter();
end_comment

begin_comment
comment|//            formatter.printHelp("ClerezzaQuery", options, true);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static Options buildOptions() {
end_comment

begin_comment
comment|//        Options result = new Options();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        OptionGroup query = new OptionGroup();
end_comment

begin_comment
comment|//        OptionBuilder.withArgName("path");
end_comment

begin_comment
comment|//        OptionBuilder.hasArg();
end_comment

begin_comment
comment|//        OptionBuilder.withDescription("LD Path to evaluate on the file starting from the context");
end_comment

begin_comment
comment|//        Option path = OptionBuilder.create("path");
end_comment

begin_comment
comment|//        OptionBuilder.withArgName("file");
end_comment

begin_comment
comment|//        OptionBuilder.hasArg();
end_comment

begin_comment
comment|//        OptionBuilder.withDescription("LD Path program to evaluate on the file starting from the context");
end_comment

begin_comment
comment|//        Option program = OptionBuilder.create("program");
end_comment

begin_comment
comment|//        query.addOption(path);
end_comment

begin_comment
comment|//        query.addOption(program);
end_comment

begin_comment
comment|//        query.setRequired(true);
end_comment

begin_comment
comment|//        result.addOptionGroup(query);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        OptionBuilder.withArgName("rdfData");
end_comment

begin_comment
comment|//        OptionBuilder.hasArg();
end_comment

begin_comment
comment|//        OptionBuilder.withDescription("File system path of the file holding RDF data");
end_comment

begin_comment
comment|//        Option filePath = OptionBuilder.create("rdfData");
end_comment

begin_comment
comment|//        filePath.setRequired(true);
end_comment

begin_comment
comment|//        result.addOption(filePath);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        OptionBuilder.withArgName("uri");
end_comment

begin_comment
comment|//        OptionBuilder.hasArg();
end_comment

begin_comment
comment|//        OptionBuilder.withDescription("URI of the context node to start from");
end_comment

begin_comment
comment|//        Option context = OptionBuilder.create("context");
end_comment

begin_comment
comment|//        context.setRequired(true);
end_comment

begin_comment
comment|//        result.addOption(context);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        return result;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//}
end_comment

end_unit

