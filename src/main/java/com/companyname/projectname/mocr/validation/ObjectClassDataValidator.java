package com.companyname.projectname.mocr.validation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.companyname.projectname.mocr.pojo.ObjectClass;
import com.companyname.projectname.mocr.xml.ObjectClassXMLPaser;

@Component
public class ObjectClassDataValidator {

	@Autowired
	private ObjectClassXMLPaser ocxp; 
	
	public void checkData(ArrayList<ObjectClass> objclsListFormIn) throws ObjectClassDataValidationException,Exception{
		
		ArrayList<ObjectClass> objclsListXmlIn = ocxp.objectClassMapping();
		
		for (ObjectClass objclsItemFormIn:objclsListFormIn){
			
			boolean parentsMark = false;
			
			if(!objclsItemFormIn.getAbbrev().equals(objclsItemFormIn.getAbbrev().toUpperCase())){
				throw new ObjectClassDataValidationException("FAILED:ObjectClass abbrev:\""+objclsItemFormIn.getAbbrev()+"\" should be all uppercase.");
			}
			
			for (ObjectClass objclsItemXmlIn:objclsListXmlIn){

				if(objclsItemXmlIn.getId().equals(objclsItemFormIn.getId())){
					throw new ObjectClassDataValidationException("FAILED:ObjectClass id:\""+objclsItemFormIn.getId()+"\" is already reserved.");
				}
				if(objclsItemXmlIn.getAbbrev().equals(objclsItemFormIn.getAbbrev())){
					throw new ObjectClassDataValidationException("FAILED:ObjectClass abbrev:\""+objclsItemFormIn.getAbbrev()+"\" is already reserved.");
				}
				if(objclsItemXmlIn.getId().equals(objclsItemFormIn.getParents())){
					parentsMark = true;
				}
			}
			
			for (ObjectClass objclsItemFormInInner:objclsListFormIn){
				if(objclsItemFormInInner.getId().equals(objclsItemFormIn.getParents())){
					parentsMark = true;
				}
			}
			
			if (!parentsMark){
				throw new ObjectClassDataValidationException("FAILED:ObjectClass parent:\""+objclsItemFormIn.getParents()+"\" is not existing.");
			}
		}
		
	}
	
	public void checkMOCRId(String mocrid,int latestMoCrId) throws ObjectClassDataValidationException,Exception{
		if (Integer.parseInt(mocrid) <= latestMoCrId ){
			throw new ObjectClassDataValidationException("FAILED:MO CR ID:\""+mocrid+"\" is not valid anymore, please refresh and recommit again.");
		}
	}
}
