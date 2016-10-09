/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ics475_gene_mutation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import static java.text.NumberFormat.Field.INTEGER;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class gene_hw implements ActionListener  {
    
    JFrame frame;
    JPanel panel;
    JPanel panel2;
    JPanel panel3;
    JTextField gene1;
    JTextField gene2;
    JComboBox cb1;
    JComboBox cb2;
    JButton submit;
    JButton quit;
    JLabel output;
    
    public gene_hw()  {
        gene1 = new JTextField("original.txt");
        gene2 = new JTextField("mutant.txt");
        submit = new JButton("Compare");
        submit.addActionListener(this);
        quit = new JButton("Close");
        quit.addActionListener(this);
        String[] choices = {("5' 3'"), ("3' 5'"), ("mRNA")};
        cb1 = new JComboBox(choices);
        cb2 = new JComboBox(choices);
        output = new JLabel((""), SwingConstants.LEFT);
        
        panel = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        
        frame = new JFrame();
        frame.setSize(500,300);
        
        panel.setLayout(new GridLayout(0,3));
        panel2.setLayout(new GridLayout(0,2));
        panel3.setLayout(new GridLayout(0,1));
        panel3.add(output);
        panel.add(new JLabel(("First File Location: "), SwingConstants.CENTER));
        panel.add(gene1);
        panel.add(cb1);
        panel.add(new JLabel(("Second File Location: "), SwingConstants.CENTER));
        panel.add(gene2);
        panel.add(cb2);
        panel2.add(submit);
        panel2.add(quit);
        
        frame.add(panel, BorderLayout.NORTH);
        frame.add(panel3);
        frame.add(panel2, BorderLayout.SOUTH);
        
//        frame.pack();
        frame.setVisible(true);
       
    }

    public static void main(String[] args) {
        new gene_hw();
    }

    private String readFile(String url) {
        BufferedReader br = null;
        String ignore;
        String sequence;
        try {
            br = new BufferedReader(new FileReader(url));
            if ((ignore = br.readLine()) != null) {
                sequence = br.readLine();
                return sequence;
            }
        } catch (IOException e) {
            System.out.println("ERROR: File path does not exist!");
            output.setText("<html><div style=\"margin-left: 10px;\">ERROR: File path does not exist!</div></html>");
        }
        return null;
    }

    private String convert_to_backward_strand(String gene) {
        String backward_strand = gene;
        char[] char_array = backward_strand.toCharArray();
        for (int i = 0; i < gene.length(); i++) {
            if (Character.toUpperCase(gene.charAt(i)) == 'A') {
                char_array[i] = 'T';
                backward_strand = String.valueOf(char_array);
            } else if (Character.toUpperCase(gene.charAt(i)) == 'T') {
                char_array[i] = 'A';
                backward_strand = String.valueOf(char_array);
            } else if (Character.toUpperCase(gene.charAt(i)) == 'C') {
                char_array[i] = 'G';
                backward_strand = String.valueOf(char_array);
            } else if (Character.toUpperCase(gene.charAt(i)) == 'G') {
                char_array[i] = 'C';
                backward_strand = String.valueOf(char_array);
            }
        }
        return backward_strand;
    }
    
    //type: 0 if gene is backward_strand, 1 if gene is forward_strand
    private String convert_to_mRNA(String gene, int type){
        String mRNA = gene;
        //this is for backward_strand
        if(type == 0){
            char[] char_array = mRNA.toCharArray();
            for(int i = 0; i < gene.length(); i++){
                if (Character.toUpperCase(gene.charAt(i)) == 'A') {
                    char_array[i] = 'U';
                    mRNA = String.valueOf(char_array);
                } else if (Character.toUpperCase(gene.charAt(i)) == 'T') {
                    char_array[i] = 'A';
                    mRNA = String.valueOf(char_array);
                } else if (Character.toUpperCase(gene.charAt(i)) == 'C') {
                    char_array[i] = 'G';
                    mRNA = String.valueOf(char_array);
                } else if (Character.toUpperCase(gene.charAt(i)) == 'G') {
                    char_array[i] = 'C';
                    mRNA = String.valueOf(char_array);
                }
            }
            return mRNA;
        } 
        //if type is not 0 (meaning its forward strand), change T to U 
        else {
            char[] char_array = mRNA.toCharArray();
            for(int i = 0; i < gene.length(); i++){
                if (Character.toUpperCase(gene.charAt(i)) == 'T') {
                    char_array[i] = 'U';
                    mRNA = String.valueOf(char_array);
                }
            }
            return mRNA;
        }
    }
    
    private int nucleotides_mutation(String gene1, String gene2){
        char[] ar1 = gene1.toCharArray();
        char[] ar2 = gene2.toCharArray();
        int count = 0;
        for(int i = 0; i < gene1.length(); i++){
            if(ar1[i] != ar2[i]){
                count ++;
            }
        }
        return count;
    }
    
    private int amino_acid_mutation (String gene1, String gene2){
        char[] ar1 = gene1.toCharArray();
        char[] ar2 = gene2.toCharArray();
        int count = 0;
        for(int i = 0; i < gene1.length(); i++){
            if(ar1[i] != ar2[i]){
                count ++;
            }
        }
        return count;
    }
    
    private String convert_to_amino_acid(String gene){
        char[] char_array = gene.toCharArray();
        String temp;
        StringBuilder amino_acid;
        if(gene.length() % 3 == 0){
            amino_acid = new StringBuilder();
            for(int i = 0; i < gene.length(); i += 3){
                temp = "";
                temp += char_array[i];
                temp += char_array[i+1];
                temp += char_array[i+2];
                if(temp.equals("GCU") || temp.equals("GCC") || temp.equals("GCA") || temp.equals("GCG")){
                    amino_acid.append('A');
                } else if (temp.equals("CGU") || temp.equals("CGC") || temp.equals("CGA") || temp.equals("CGG") || 
                        temp.equals("AGA") || temp.equals("AGG")){
                    amino_acid.append('R');
                } else if (temp.equals("AUU") || temp.equals("AAC")){ 
                    amino_acid.append('N');
                } else if (temp.equals("GAU") || temp.equals("GAC")){ 
                    amino_acid.append('D');
                } else if (temp.equals("UGU") || temp.equals("UGC")){ 
                    amino_acid.append('C');
                } else if (temp.equals("CAA") || temp.equals("CAG")){ 
                    amino_acid.append('Q');
                } else if (temp.equals("GAA") || temp.equals("GAG")){ 
                    amino_acid.append('E');
                } else if (temp.equals("GGU") || temp.equals("GGC") || temp.equals("GGA") || temp.equals("GGG")){ 
                    amino_acid.append('G');
                } else if (temp.equals("CAU") || temp.equals("CAC")){ 
                    amino_acid.append('H');
                } else if (temp.equals("AUU") || temp.equals("AUC") || temp.equals("AUA")){ 
                    amino_acid.append('I');
                } else if (temp.equals("UUA") || temp.equals("UUG") || temp.equals("CUU") || temp.equals("CUC") 
                        || temp.equals("CUA") || temp.equals("CUG")){ 
                    amino_acid.append('L');
                } else if (temp.equals("AAA") || temp.equals("AAG")){ 
                    amino_acid.append('K');
                } else if (temp.equals("AUG")){
                    amino_acid.append('M');
                } else if (temp.equals("UUU") || temp.equals("UUC")){ 
                    amino_acid.append('F');
                }  else if (temp.equals("CCT") || temp.equals("CCC") || temp.equals("CCA") || temp.equals("CCG")){
                    amino_acid.append('P');
                } else if (temp.equals("UCU") || temp.equals("UCC") || temp.equals("UCA")  || temp.equals("UCG") 
                        || temp.equals("AGU") || temp.equals("AGC")){
                    amino_acid.append('S');
                } else if (temp.equals("ACU") || temp.equals("ACC") || temp.equals("ACA") || temp.equals("ACG")){
                    amino_acid.append('T');
                } else if (temp.equals("UGG")){
                    amino_acid.append('W');
                } else if (temp.equals("UAU") || temp.equals("UAC")){
                    amino_acid.append('Y');
                } else if (temp.equals("GUU") || temp.equals("GUC") || temp.equals("GUA") || temp.equals("GUG")){
                    amino_acid.append('V');
                } 
            }
            return amino_acid.toString();
            
        } else {
            return ("ERROR: Does not have enough Nucleotide Codon to determine Amino Acid");
        }
    }
    
    private String mutation_nucleotides(String gene1, String gene2){
        char[] ar1 = gene1.toCharArray();
        char[] ar2 = gene2.toCharArray();
        StringBuilder sb = new StringBuilder();
        String temp;
        String temp2;
        for(int i = 0; i < gene1.length(); i++){
            temp = "";
            if(ar1[i] != ar2[i]){
                temp += ar1[i];
                temp += i + 1; 
                temp += ar2[i];
                if(sb.length() == 0){
                    sb.append(temp);
                }  else {
                    temp2 = ", " + temp;
                    sb.append(temp2);
                }
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == this.quit) {
            System.exit(0);
        }
        else if (event.getSource() == this.submit) {
            String get_file1 = gene1.getText();
            String get_file2 = gene2.getText();
            String file1 = this.readFile(get_file1);
            String file2 = this.readFile(get_file2);
            String gene1_category;
            String gene2_category;
            String backward_strand_1;
            String backward_strand_2;
            String amino_acid_1;
            String amino_acid_2;
            String mrna_1;
            String mrna_2;
            String mutation_nucleotide;
            int nucleotides_mutation;
            int amino_acid_mutation;
            if(file1.length() == file2.length()){
                //System.out.println("Length of both sequence match! \n");
                gene1_category = cb1.getSelectedItem().toString();
                gene2_category = cb2.getSelectedItem().toString();
                switch(gene1_category){
                    case("5' 3'"): 
                        backward_strand_1 = this.convert_to_backward_strand(file1);
                        mrna_1 = this.convert_to_mRNA(backward_strand_1, 0);
                        break;
                    case("3' 5'"):
                        mrna_1 = this.convert_to_mRNA(file1, 1);
                        break;
                    case("mRNA"):
                        mrna_1 = this.convert_to_mRNA(file1, 1);
                        break;
                    default:
                        mrna_1 = "ERROR!";
                        break;
                }
                switch(gene2_category){
                    case("5' 3'"): 
                        backward_strand_2 = this.convert_to_backward_strand(file2);
                        mrna_2 = this.convert_to_mRNA(backward_strand_2, 0);
                        break;
                    case("3' 5'"):
                        mrna_2 = this.convert_to_mRNA(file2, 1);
                        break;
                    case("mRNA"):
                        mrna_2 = this.convert_to_mRNA(file2, 1);
                        break;
                    default:
                        mrna_2 = "ERROR!";
                        break;
                }
                System.out.println(mrna_1);
                System.out.println(mrna_2);
                amino_acid_1 = this.convert_to_amino_acid(mrna_1);
                amino_acid_2 = this.convert_to_amino_acid(mrna_2);
                nucleotides_mutation = this.nucleotides_mutation(file1, file2);
                amino_acid_mutation = this.amino_acid_mutation(amino_acid_1, amino_acid_2);
                mutation_nucleotide = this.mutation_nucleotides(mrna_1, mrna_2);
                output.setText("<html> <div style=\"margin-left: 10px;\">" + 
                        "Mutation(s) nucleotide: " + mutation_nucleotide + "<br>" +
                        "Number of nucleotide mutation(s): " + nucleotides_mutation + "<br>" +
                        "Number of amino acid mutation(s): " + amino_acid_mutation + "<br>" +
                        "Sequence 1 Nucleotides: " + file1 + "<br>" +
                        "Sequence 2 Nucleotides: " + file2 + "<br>" +
                        "Sequence 1 Amino Acids: " + amino_acid_1 + "<br>" +
                        "Sequence 2 Amino Acids: " + amino_acid_2 + "<br>" +
                        "</div> </html>");
                
                System.out.println(amino_acid_1);
                System.out.println(amino_acid_2);
                System.out.println(nucleotides_mutation);
                System.out.println(amino_acid_mutation);
                
            } else {
                //System.out.println("Error: Difference sequence length!\n");
                output.setText("Error: Difference sequence length!");
            }
        }
    }
}
