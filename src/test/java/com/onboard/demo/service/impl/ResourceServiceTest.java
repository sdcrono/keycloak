package com.onboard.demo.service.impl;

import com.onboard.demo.error.BadRequestException;
import com.onboard.demo.error.ResourceNotFoundException;
import com.onboard.demo.model.Category;
import com.onboard.demo.model.Resource;
import com.onboard.demo.model.request.ResourceRequest;
import com.onboard.demo.repository.CategoryRepository;
import com.onboard.demo.repository.ResourceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ResourceServiceTest {

    @InjectMocks
    private ResourceServiceImpl resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testFindAllWithSuccessfully() {
        when(resourceRepository.findAll()).thenReturn(Collections.emptyList());
        List<Resource> actualResources = resourceService.findAll();
        assertThat(actualResources, equalTo(Collections.EMPTY_LIST));
    }

    @Test
    public void testGetResourceSuccessfully() throws ResourceNotFoundException {
        Resource expectedResource = new Resource();
        expectedResource.setName("abc");
        expectedResource.setActive("true");

        when(resourceRepository.getOne(anyLong())).thenReturn(expectedResource);

        Resource actualResource = resourceService.get(anyLong());

        assertThat(actualResource.getName(), equalTo(expectedResource.getName()));
        assertThat(actualResource.getActive(), equalTo(expectedResource.getActive()));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testGetResourceWithInvalidId() throws ResourceNotFoundException {
        Resource expectedResource = new Resource();
        expectedResource.setName("abc");
        expectedResource.setActive("true");

        doThrow(MockitoException.class).when(resourceRepository).getOne(anyLong());

        Resource actualResource = resourceService.get(anyLong());

        assertThat(actualResource.getName(), equalTo(expectedResource.getName()));
        assertThat(actualResource.getActive(), equalTo(expectedResource.getActive()));
    }

    @Test
    public void testSaveWithSuccessfully() throws Exception {
        ResourceRequest resourceRequest = new ResourceRequest();
        resourceRequest.setName("abc");
        resourceRequest.setActive("true");
        resourceRequest.setCategory("M");

        Category category = new Category();
        category.setName("M");

        when(categoryRepository.findOneByName(anyString())).thenReturn(Optional.of(category));

        Resource actualResource = resourceService.save(resourceRequest);

        assertThat(actualResource.getName(), equalTo("abc"));
        assertThat(actualResource.getActive(), equalTo("true"));
        assertThat(actualResource.getCategory().getName(), equalTo("M"));
    }

    @Test(expected = BadRequestException.class)
    public void testSaveWithMissingName() throws Exception {
        ResourceRequest resourceRequest = new ResourceRequest();
        resourceRequest.setUser("Andy");
        resourceRequest.setCategory("M");
        resourceRequest.setType("A");
        resourceRequest.setActive("true");

        resourceService.save(resourceRequest);
    }

    @Test(expected = BadRequestException.class)
    public void testSaveWithInvalidCategory() throws Exception {
        ResourceRequest resourceRequest = new ResourceRequest();
        resourceRequest.setName("abc");
        resourceRequest.setCategory("M");

        when(categoryRepository.findOneByName(anyString())).thenReturn(Optional.empty());

        resourceService.save(resourceRequest);
    }

    @Test
    public void testUpdateSuccessfully() throws Exception {
        ResourceRequest resourceRequest = new ResourceRequest();
        resourceRequest.setName("abc");
        resourceRequest.setActive("true");
        resourceRequest.setCategory("M");

        Category category = new Category();
        category.setName("M");

        Resource existedResource = new Resource();
        existedResource.setName("def");
        existedResource.setActive("false");

        Category existedCategory = new Category();
        existedCategory.setName("N");
        existedResource.setCategory(existedCategory);

        when(resourceRepository.findById(anyLong())).thenReturn(Optional.of(existedResource));
        when(categoryRepository.findOneByName(anyString())).thenReturn(Optional.of(category));

        Resource actualResource = resourceService.update(anyLong(), resourceRequest);
        assertThat(actualResource.getName(), equalTo("abc"));
        assertThat(actualResource.getActive(), equalTo("true"));
        assertThat(actualResource.getCategory().getName(), equalTo("M"));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateWithInvalidResource() throws Exception {
        ResourceRequest resourceRequest = new ResourceRequest();
        resourceRequest.setName("abc");

        when(resourceRepository.findById(anyLong())).thenReturn(Optional.empty());

        resourceService.update(anyLong(), resourceRequest);
    }

    @Test
    public void testDeleteResourceSuccessfully() throws ResourceNotFoundException {
        doNothing().when(resourceRepository).deleteById(anyLong());

        boolean actualResult = resourceService.delete(1L);

        assertThat(actualResult, equalTo(true));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteResourceWithInvalidId() throws ResourceNotFoundException {
        doThrow(MockitoException.class).when(resourceRepository).deleteById(anyLong());

        resourceService.delete(1L);
    }
}